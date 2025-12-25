package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 通联支付 扫码枪扫二维码支付参数
 */
@Data
public class TongLianPayParam implements Serializable {
    private static final long serialVersionUID = -4991319078848740372L;

    @ApiModelProperty(value = "交易金额,单位为分")
    private long trxamt;

    @ApiModelProperty(value = "商户交易单号")
    private String reqsn;

    @ApiModelProperty(value = "订单标题")
    private String body;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "W01 微信扫码支付，A01 支付宝扫码支付")
    private String paytype;

    @ApiModelProperty(value = "支付后返回数据")
    private Map<String, String> payResult;

}
