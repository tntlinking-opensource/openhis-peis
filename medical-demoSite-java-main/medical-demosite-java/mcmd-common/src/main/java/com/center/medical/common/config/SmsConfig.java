package com.center.medical.common.config;

import lombok.Data;

import java.io.Serializable;

/**
 * 短信配置
 */
@Data
public class SmsConfig implements Serializable {
    private static final long serialVersionUID = 7981600516477419079L;

    //主账户ID
    private String accountSid;

    //账户授权令牌
    private String authToken;


    public SmsConfig(String accountSid, String authToken) {
        this.accountSid = accountSid;
        this.authToken = authToken;
    }


    public SmsConfig() {
    }
}
