package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 通联支付 扫码枪扫二维码支付参数
 */
@Data
public class TongLianScanPayParam implements Serializable {
    private static final long serialVersionUID = -4991319078848740372L;

    @ApiModelProperty(value = "交易金额,单位为分")
    private long trxamt;

    @ApiModelProperty(value = "商户交易单号")
    private String reqsn;

    @ApiModelProperty(value = "订单标题")
    private String body;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "支付授权码")
    private String authcode;

    @ApiModelProperty(value = "支付限制")
    private String limit_pay;

    @ApiModelProperty(value = "证件号,实名交易必填.填了此字段就会验证证件号和姓名")
    private String idno;

    @ApiModelProperty(value = "付款人真实姓名")
    private String truename;

    @ApiModelProperty(value = "分账信息")
    private String asinfo;

    @ApiModelProperty(value = "支付后返回数据")
    private Map<String, String> payResult;

    @ApiModelProperty(value = "类型：9通联支付、11通联支付2")
    private Integer type;

}
