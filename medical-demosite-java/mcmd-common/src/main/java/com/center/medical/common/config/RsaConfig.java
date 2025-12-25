/*
 * Copyright (c) 2022-2999 青岛沃德互联网医疗科技有限公司 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.common.config;

import lombok.Data;

/**
 * Rsa密钥信息
 *
 * @author 路飞
 */
@Data
public class RsaConfig {

    /**
     * Rsa公钥
     */
    private String publicKey;

    /**
     * Rsa私钥
     */
    private String privateKey;

    /**
     * 授权码
     */
    private String authCode;

    /**
     * 地址
     */
    private String adress;
}
