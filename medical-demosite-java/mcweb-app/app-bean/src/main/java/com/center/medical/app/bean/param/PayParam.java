package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 支付参数
 */
@Data
@ApiModel(value = "支付参数")
public class PayParam {

    @NotNull(message = "订单号不能为空")
    @ApiModelProperty(value = "订单号", required = true)
    private String orderId;

    @NotNull(message = "金额不能为空")
    @ApiModelProperty(value = "金额", required = true)
    private Double money;

    @NotNull(message = "订单类型不能为空")
    @ApiModelProperty(value = "订单类型 1:购买套餐", required = true)
    private Integer orderOrigin;

    @NotNull(message = "支付方式不能为空")
    @ApiModelProperty(value = "支付方式 (1:微信小程序支付 2:支付宝 3微信扫码支付 4 微信h5支付 5微信公众号支付 6支付宝H5支付 7支付宝APP支付 8微信APP支付 9余额支付 10全球PayPal支付 11随行支付)", required = true)
    private Integer payType;

    @ApiModelProperty(value = "支付完成回跳地址", required = true)
    private String returnUrl;

    @ApiModelProperty(value = "分中心id", required = true)
    private String fzxId;
}
