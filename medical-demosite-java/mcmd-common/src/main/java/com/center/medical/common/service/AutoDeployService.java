package com.center.medical.common.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.SystemUtil;
import com.center.medical.common.bean.dto.DeployVersionDto;
import com.center.medical.common.bean.param.AutoDeploySaveRecordParam;
import com.center.medical.common.config.AutoDeployConfig;
import com.center.medical.common.constant.AutoDeployConstant;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.uuid.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;

/**
 * 自动更新
 * @author xhp
 * @since 2023-11-22 7:37
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AutoDeployService {
    private final AutoDeployConfig autoDeployConfig;
    private final ShutdownEndpoint shutdownEndpoint;

    /**
     * 检查配置文件是否正确配置
     * @return
     */
    public boolean checkConfig(){
        log.info("检查配置文件是否正确配置");
        Integer updateType=autoDeployConfig.getUpdateType();
        if(updateType==null){
            log.info("deploy.updateType不能为空");
            return false;
        }
        String mainUrl=autoDeployConfig.getMainUrl();
        if(StrUtil.isBlank(mainUrl)){
            log.info("deploy.mainUrl不能为空");
            return false;
        }
        return true;
    }

    /**
     * 获取最新版本
     * @return
     */
//    @Retryable(maxAttempts = 5,backoff = @Backoff(delay = 5*60*1000))
    @Retryable(maxAttempts = 3,backoff = @Backoff(delay = 1000))
    public DeployVersionDto getVersion(){
        log.info("获取最新版本...");
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("updateType", autoDeployConfig.getUpdateType());
        paramMap.put("ksIp", SystemUtil.getHostInfo().getAddress());
        String result= HttpUtil.get(autoDeployConfig.getMainUrl()+AutoDeployConstant.GET_VERSION_PATH, paramMap,AutoDeployConstant.HTTP_TIMEOUT);
        log.info("最新版本信息："+result);
        R<DeployVersionDto> deployVersionDtoR= JSONUtil.toBean(result,R.class);
        if(R.SUCCESS!=deployVersionDtoR.getCode()||deployVersionDtoR.getData()==null){
            throw new ServiceException(deployVersionDtoR.getMsg());
        }
        DeployVersionDto deployVersionDto=deployVersionDtoR.getData();
        deployVersionDto.setMainUrl(autoDeployConfig.getMainUrl());
        deployVersionDto.setDownloadJarPath(System.getProperty("user.dir")+ File.separator+ UUID.randomUUID()+".jar");
        String jarPath=AutoDeployService.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        deployVersionDto.setJarPath(jarPath.substring(jarPath.indexOf(":")+1,jarPath.indexOf("!")));
        return deployVersionDto;
    }


    //recover方法返回值必须和Retryable方法一样，如果没有匹配的recover方法会将异常抛出，否则不会抛出异常。可以在@Retryable注解中指定recover方法
//    @Recover
//    public void recover(Exception e){
//        log.info("recover running...");
//        log.info(e.getMessage());
//    }

    /**
     * 启动更新服务
     */
    public void startDeployService(String deployStartupCommand){
        try{
            log.info("启动更新服务，{}",deployStartupCommand);
            RuntimeUtil.exec(deployStartupCommand);
        }catch (Exception e){
            throw new ServiceException("启动更新服务命令执行失败");
        }
        log.info("启动更新服务命令已执行");
    }

    /**
     * 下载jar包
     * @param filePath
     * @return 返回jar包路径
     */
    public void downloadJar(String filePath,String destPath){
        log.info("下载jar包,{},{}",filePath,destPath);
        HttpUtil.downloadFile(autoDeployConfig.getMainUrl()+ AutoDeployConstant.DOWNLOAD_JAR_PATH+"?fileName="+filePath
                , FileUtil.file(destPath),AutoDeployConstant.DOWNLOAD_TIMEOUT);
        log.info("下载jar包成功");
    }

    /**
     * 给自动更新服务发送请求，判断服务启动成功，并开启更新线程。
     */
    //    @Retryable(maxAttempts = 3,backoff = @Backoff(delay = 5*60*1000))
    @Retryable(maxAttempts = 3,backoff = @Backoff(delay = 1000))
    public void startDeploy(DeployVersionDto deployVersionDto){
        log.info("开始更新...");
        String result=HttpRequest.post(deployVersionDto.getDeployServiceAddr()+AutoDeployConstant.START_DEPLOY_PATH)
                .body(JSONUtil.toJsonStr(deployVersionDto))
                .contentType(ContentType.JSON.getValue())
                .timeout(AutoDeployConstant.HTTP_TIMEOUT)
                .execute()
                .body()
                ;
        R r=JSONUtil.toBean(result,R.class);
        if(R.SUCCESS!=r.getCode()){
            throw new ServiceException(r.getMsg());
        }
        log.info("开始更新成功");
    }

    /**
     * 关闭自己
     */
    public void shutdown(){
        log.info("自我关闭...");
        shutdownEndpoint.shutdown();
    }

    /**
     * 保存更新结果
     * @param deployVersionDto
     * @param isSuccess
     * @param message
     */
    //    @Retryable(maxAttempts = 3,backoff = @Backoff(delay = 5*60*1000))
    @Retryable(maxAttempts = 3,backoff = @Backoff(delay = 1000))
    public void saveRecord(DeployVersionDto deployVersionDto,int isSuccess,String message){
        log.info("保存更新结果");
        AutoDeploySaveRecordParam param=new AutoDeploySaveRecordParam();
        param.setIsSuccess(isSuccess);
        param.setMessage(message);
        param.setVersionId(deployVersionDto.getId());
        param.setKsIpId(deployVersionDto.getKsIpId());
        param.setMessage(message);
        param.setAddress(SystemUtil.getHostInfo().getAddress());
        param.setHostName(SystemUtil.getHostInfo().getName());
        String body=HttpRequest.post(deployVersionDto.getMainUrl()+AutoDeployConstant.SAVE_RECORD_PATH)
                .timeout(10*1000)
                .contentType(ContentType.JSON.getValue())
                .body(JSONUtil.toJsonStr(param))
                .execute()
                .body();
        JSONObject jo=JSONUtil.parseObj(body);
        if(HttpStatus.HTTP_OK!=jo.getInt("code")){
            throw new RuntimeException("保存更新结果失败，"+jo.getStr("msg"));
        }
        log.info("保存更新结果成功");
    }
}
