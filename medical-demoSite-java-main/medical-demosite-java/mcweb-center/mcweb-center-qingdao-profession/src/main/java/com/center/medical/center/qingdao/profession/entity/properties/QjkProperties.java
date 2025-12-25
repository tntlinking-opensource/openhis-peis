package com.center.medical.center.qingdao.profession.entity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "qjk")
public class QjkProperties {

    private String open;
    private String startDate;
    private String endDate;
    private String place;

    //jdbc 连接设置
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}