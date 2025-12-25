package com.center.medical.center.deploy;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RuntimeUtil;
import com.center.medical.center.deploy.config.DeployConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 自动部署
 * @author xhp
 * @since 2023-11-09 10:10
 * Deprecated by 改成被更新服务启动此服务后，调用此服务接口确定此服务已启动、关闭自己、开始更新。
 */
@Deprecated
@Service
@Slf4j
@RequiredArgsConstructor
public class AutoDeployer {
    private final DeployConfig deployConfig;
//    @PostConstruct
    public void deploy(){
        log.info("自动部署开始{}",deployConfig);

        //Caused by: java.nio.file.FileSystemException: C:\Users\Administrator\Desktop\测试自动更新\demos-0.0.1-SNAPSHOT.jar: 另一个程序正在使用此文件，进程无法访问。
        log.info("开始复制jar包");
        FileUtil.copy(deployConfig.getSourcePath(),deployConfig.getTargetPath(),true);
        log.info("复制jar包成功");

        log.info("开始启动服务");
        //java -jar启动后不会退出，导致更新服务也会卡在这里。如果停止更新服务，通过命令行启动的服务也会停止。 javaw效果一样。
//        String execStr=RuntimeUtil.execForStr(deployConfig.getStartupCommand());
//        log.info("启动服务成功{}",execStr);
        RuntimeUtil.exec(deployConfig.getStartupCommand());
        log.info("启动服务成功{}");

        log.info("自动部署成功");
    }
}
