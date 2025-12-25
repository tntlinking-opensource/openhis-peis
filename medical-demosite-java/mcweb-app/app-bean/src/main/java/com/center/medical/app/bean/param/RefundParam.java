package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 退款参数
 */
@Data
public class RefundParam implements Serializable {
    private static final long serialVersionUID = 2657500587212745749L;

    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "支付方式 (1:微信小程序支付 2:支付宝 3微信扫码支付 4 微信h5支付 5微信公众号支付 6支付宝H5支付 7支付宝APP支付 8微信APP支付 9余额支付 10全球PayPal支付 11随行支付)", required = true)
    private Integer payType;
}
