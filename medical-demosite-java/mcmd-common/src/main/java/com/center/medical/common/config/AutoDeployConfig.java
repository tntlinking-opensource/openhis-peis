package com.center.medical.common.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xhp
 * @since 2023-11-22 7:39
 */
@Component
@ConfigurationProperties(prefix = "deploy")
@Data
public class AutoDeployConfig {
    //主服务地址
    private String mainUrl;

    //更新类型 详见com.center.medical.bean.enums.DeployType
    private Integer updateType;

}
