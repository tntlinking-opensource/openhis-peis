package com.center.medical.bean.param;

import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2023/4/12 16:26
 * @description: 付款码支付参数
 */
@Data
public class MicropayParam implements Serializable {
    private static final long serialVersionUID = -269099853817906499L;

    @ApiModelProperty(value = "扫码设备号", position = 0, required = true)
    private String deviceInfo;

    @ApiModelProperty(value = "付款码：扫码支付付款码，设备读取用户微信中的条码或者二维码信息", position = 1, required = true)
    private String authCode;

    @ApiModelProperty(value = "商户订单号")
    private String outTradeNo;

    @ApiModelProperty(value = "订单金额")
    private Integer totalFee;

    @ApiModelProperty(value = "商品描述")
    private String body;

    @ApiModelProperty(value = "终端IP")
    private String bodspbillCreateIpy;

    @ApiModelProperty(value = "扣款方式：3.微星 4.支付宝")
    private Integer kkfs;


    @ApiModelProperty(value = "返回对象")
    private WxPayMicropayResult wxPayMicropayResult;
}
