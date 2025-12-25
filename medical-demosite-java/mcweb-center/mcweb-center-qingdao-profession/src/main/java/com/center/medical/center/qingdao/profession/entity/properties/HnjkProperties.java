package com.center.medical.center.qingdao.profession.entity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 胶州疾控
 */
@Data
@ConfigurationProperties(prefix = "hnjk")
public class HnjkProperties {

    //登录信息
    private Boolean open;//是否开启
    private String url;//登录网址
    private String userCode;//登录用户名
    private String password;//登录密码


    //上传数据信息
    private String ohOrgCode;//职业健康检查机构编码
    private String orgName;//机构名称
}