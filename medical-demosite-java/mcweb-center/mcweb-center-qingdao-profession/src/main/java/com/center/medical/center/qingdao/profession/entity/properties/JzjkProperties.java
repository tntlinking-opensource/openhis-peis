package com.center.medical.center.qingdao.profession.entity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 胶州疾控
 */
@Data
@ConfigurationProperties(prefix = "jzjk")
public class JzjkProperties {

    private Boolean open;

    private String url;

    private String userCode;

    private String password;

}