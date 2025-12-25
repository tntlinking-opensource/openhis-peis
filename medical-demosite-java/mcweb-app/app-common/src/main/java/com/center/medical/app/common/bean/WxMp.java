/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.common.bean;


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
