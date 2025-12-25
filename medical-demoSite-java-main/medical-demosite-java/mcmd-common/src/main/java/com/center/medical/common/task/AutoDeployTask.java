package com.center.medical.common.task;

import com.center.medical.common.bean.dto.DeployVersionDto;
import com.center.medical.common.service.AutoDeployService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 自动更新线程（所有需要自动更新的服务，除了主服务）
 * @author xhp
 * @since 2023-11-21 9:49
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AutoDeployTask {
    private final AutoDeployService autoDeployService;

//    @Scheduled(cron = "${deploy.cron:-}")
    public void autoDeploy() {
        log.info("自动更新线程启动");

        //检查配置文件是否正确配置
        if(!autoDeployService.checkConfig())return;

        //调用主服务接口，获取最新版本信息
        DeployVersionDto deployVersionDto=autoDeployService.getVersion();

        try{
            //调用主服务接口，下载jar包
            autoDeployService.downloadJar(deployVersionDto.getFilePath(),deployVersionDto.getDownloadJarPath());

            //执行命令，启动更新服务
            autoDeployService.startDeployService(deployVersionDto.getDeployStartupCommand());

            //调用自动更新服务接口，判断服务启动成功，并开启更新线程。
            autoDeployService.startDeploy(deployVersionDto);

            //自动更新服务启动成功，关闭自己
            autoDeployService.shutdown();
        }catch(Exception e){
            log.info("更新失败",e);
            //调用主服务接口，保存更新失败记录
            autoDeployService.saveRecord(deployVersionDto,0,e.getMessage());
        }
    }
}
