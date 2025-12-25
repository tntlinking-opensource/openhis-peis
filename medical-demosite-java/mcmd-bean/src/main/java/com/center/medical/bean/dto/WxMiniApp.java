/*
 * Copyright (c) 2021-2999 沃德国际 All rights reserved.
 *
 * https://www.maiwd.cn/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.bean.dto;


import lombok.Data;

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
