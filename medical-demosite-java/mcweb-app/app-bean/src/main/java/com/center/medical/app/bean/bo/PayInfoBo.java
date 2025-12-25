/*
 * Copyright (c) 2022-2999 青岛易筑科技有限公司 All rights reserved.
 *
 * https://www.jixieguanjia.com.cn/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.bean.bo;

import lombok.Data;

/**
 * @author: 路飞
 * @date: 2022-08-20 15:04
 * @description: PayInfoBo
 */
@Data
public class PayInfoBo {

    /**
     * 支付单号
     */
    private String payNo;

    /**
     * 第三方订单流水号
     */
    private String bizPayNo;

    private Boolean isPaySuccess;

    private String successString;

    /**
     * 第三方订单订单号
     */
    private String bizOrderNo;

}
