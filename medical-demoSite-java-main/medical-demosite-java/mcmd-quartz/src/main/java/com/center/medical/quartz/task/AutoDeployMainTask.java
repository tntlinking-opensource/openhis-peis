package com.center.medical.quartz.task;

import com.center.medical.common.bean.dto.DeployVersionDto;
import com.center.medical.common.service.AutoDeployService;
import com.center.medical.quartz.service.AutoDeployMainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 自动更新线程（主服务）
 * @author xhp
 * @since 2023-12-04 14:22
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AutoDeployMainTask {
    private final AutoDeployMainService autoDeployMainService;
    private final AutoDeployService autoDeployService;

    /**
     * 自动更新
     */
    public void run(){
        log.info("主服务自动更新任务执行中--------------");

        //执行sql
        autoDeployMainService.updateDb();

        //检查配置文件是否正确配置
        if(!autoDeployMainService.checkConfig())return;

        //查询最新版本信息
        DeployVersionDto deployVersionDto=autoDeployMainService.getVersion();

        try{
            //执行命令，启动更新服务
            autoDeployService.startDeployService(deployVersionDto.getDeployStartupCommand());

            //调用自动更新服务接口，判断服务启动成功，并开启更新线程。
            autoDeployService.startDeploy(deployVersionDto);

            //自动更新服务启动成功，关闭自己
            autoDeployService.shutdown();
        }catch(Exception e){
            log.info("更新失败",e);
            //调用主服务接口，保存更新失败记录
            autoDeployMainService.saveRecord(deployVersionDto,0,e.getMessage());
        }
    }
}
