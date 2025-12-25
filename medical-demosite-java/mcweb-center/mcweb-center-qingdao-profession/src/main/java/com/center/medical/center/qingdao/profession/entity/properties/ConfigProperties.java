package com.center.medical.center.qingdao.profession.entity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = ConfigProperties.CONFIG)
public class ConfigProperties {
    public static final String CONFIG = "config";
    private String requestUrl;
    private String userCode;
    private String password;
    private String institutionCode;
    private String institutionName;
    private String phyexamaddressName;
    private String phyexamaddressCode;
    private String writePerson;
    private String writePersonPhone;
    private String reportPerson;
    private String reportPersonPhone;
    private Boolean hasArchive = true;
    private String resultPath = "result";
    private String startDate;
    private String endDate;
    private Integer repeatCount;
}