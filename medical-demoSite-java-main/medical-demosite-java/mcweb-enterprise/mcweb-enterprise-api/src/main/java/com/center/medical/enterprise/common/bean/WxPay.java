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
 * 微信支付参数
 *
 * @author yami
 */
@Data
public class WxPay {

    /**
     * 微信支付mchId
     */
    private String mchId;

    /**
     * 微信支付mchKey
     */
    private String mchKey;

    /**
     * 签名类型
     */
    private String signType;

    /**
     * 支付证书路径
     */
    private String keyPath;


}
