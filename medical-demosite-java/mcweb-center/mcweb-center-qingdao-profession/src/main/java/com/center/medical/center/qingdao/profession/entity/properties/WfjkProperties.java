package com.center.medical.center.qingdao.profession.entity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "wfjk")
public class WfjkProperties {

    private String open;//是否开启
    private String url;//地址
    private String appId;//第三方标识
    private String IV;//IV
    private String pkey;//pkey
    private String appSecret;//应用程序密钥
    private String deptId;//平台部门编号

}