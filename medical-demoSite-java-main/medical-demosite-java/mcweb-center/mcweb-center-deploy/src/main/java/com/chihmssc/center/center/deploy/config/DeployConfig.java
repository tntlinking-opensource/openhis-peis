package com.center.medical.center.deploy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自动部署配置
 * @author xhp
 * @since 2023-11-09 10:16
 */
@Data
@Component
@ConfigurationProperties(prefix = "deploy")
public class DeployConfig {
    //更新包路径
    private String sourcePath;
    //服务jar包路径
    private String targetPath;
    //启动命令
    private String startupCommand;
}
