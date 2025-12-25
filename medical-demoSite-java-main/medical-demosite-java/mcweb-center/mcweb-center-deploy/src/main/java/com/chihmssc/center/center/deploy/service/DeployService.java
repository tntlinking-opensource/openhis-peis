package com.center.medical.center.deploy.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.SystemUtil;
import com.center.medical.center.deploy.bean.dto.DeployVersionDto;
import com.center.medical.center.deploy.bean.param.AutoDeploySaveRecordParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * @author xhp
 * @since 2023-11-23 8:19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeployService {
    private final ShutdownEndpoint shutdownEndpoint;
    /**
     * 复制jar包
     * @param deployVersionDto
     */
    //    @Retryable(maxAttempts = 3,backoff = @Backoff(delay = 5*60*1000))
    @Retryable(maxAttempts = 3,backoff = @Backoff(delay = 1000))
    public void copyJar(DeployVersionDto deployVersionDto){
        log.info("复制jar包...");
        FileUtil.copy(deployVersionDto.getDownloadJarPath(),deployVersionDto.getJarPath(),true);
        log.info("复制jar包成功");
    }

    /**
     * 启动服务
     * @param deployVersionDto
     */
    public void startService(DeployVersionDto deployVersionDto){
        log.info("启动服务");
        //只有命令有问题时会报错，端口被占用等情况不会报错
        RuntimeUtil.exec(deployVersionDto.getServiceStartupCommand());
        log.info("启动服务命令已执行");
    }

    /**
     * 检查服务是否启动
     * @param deployVersionDto
     */
    //    @Retryable(maxAttempts = 3,backoff = @Backoff(delay = 5*60*1000))
    @Retryable(maxAttempts = 3,backoff = @Backoff(delay = 1000))
    public void checkService(DeployVersionDto deployVersionDto){
        log.info("检查服务是否启动");
        //调用spring boot actuator自带的info接口
        int status=HttpRequest.get(deployVersionDto.getServiceAddr()+"/actuator/info").timeout(5*1000).execute().getStatus();
        log.info("服务状态:"+status);
        if(status!= HttpStatus.HTTP_OK){
            throw new RuntimeException("服务未启动");
        }
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
        String body=HttpRequest.post(deployVersionDto.getMainUrl()+"/open/api/autoDeploy/record/save")
                .timeout(10*1000)
                .contentType(ContentType.JSON.getValue())
                .body(JSONUtil.toJsonStr(param))
                .execute()
                .body();
        log.info(body);
        JSONObject jo=JSONUtil.parseObj(body);
        if(HttpStatus.HTTP_OK!=jo.getInt("code")){
            throw new RuntimeException("保存更新结果失败，"+jo.getStr("msg"));
        }
    }

    /**
     * 关闭自己
     */
    public void shutdown(){
        log.info("关闭自己");
        shutdownEndpoint.shutdown();
    }
}
