package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 随行付 支付参数
 */
@Data
public class SuiXingReverseScanParam implements Serializable {
    private static final long serialVersionUID = 591052849971893056L;

    @ApiModelProperty(value = "商户订单号")
    private String ordNo;

    @ApiModelProperty(value = "订单总金额(元)，格式：#######.##")
    private String amt;

    @ApiModelProperty(value = "授权码 通过扫码枪/声波获取设备获取的支付宝/微信/银联付款码")
    private String authCode;


    @ApiModelProperty(value = "支付后返回数据")
    private Map<String, Object> payResult;


    public SuiXingReverseScanParam(String ordNo, String amt, String authCode) {
        this.ordNo = ordNo;
        this.amt = amt;
        this.authCode = authCode;
    }


    public SuiXingReverseScanParam() {
    }
}
