/*
 * Copyright (c) 2024-2999 青岛沃德国际 All rights reserved.
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.enterprise.common.bean;


import lombok.Data;

/**
 * 微信公众号参数
 *
 * @author yami
 */
@Data
public class WxMp {
    /**
     * 设置微信公众号的appid
     */
    private String appId;

    /**
     * 设置微信公众号的Secret
     */
    private String secret;

    /**
     * 微信公众号消息加解密token
     */
    private String token;

    /**
     * 微信公众号消息加解密aesKey
     */
    private String aesKey;
}
