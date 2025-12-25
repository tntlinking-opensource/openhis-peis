/*
 * Copyright (c) 2022-2999 青岛易筑科技有限公司 All rights reserved.
 *
 * https://www.jixieguanjia.com.cn/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.bean.dto;

import lombok.Data;

/**
 * @author: 路飞船长
 * @date: 2022/8/9 18:21
 * @description: 支付信息
 */
@Data
public class PayInfoDto {

    /**
     * 支付信息，如商品名称
     */
    private String body;

    /**
     * 支付单号
     */
    private String payNo;

    /**
     * 付款金额
     */
    private Double payAmount;

    /**
     * 支付方式
     */
    private Integer payType;

    /**
     * api回调域名
     */
    private String apiNoticeUrl;

    /**
     * 支付完成会跳地址
     */
    private String returnUrl;

    /**
     * 第三方用户id
     */
    private String bizUserId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 分中心id
     */
    private String fzxId;

}
