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
 * 微信小程序参数
 *
 * @author yami
 */
@Data
public class WxMiniApp {
    /**
     * 设置微信小程序的appid
     */
    private String appId;

    /**
     * 设置微信小程序的Secret
     */
    private String secret;

}
