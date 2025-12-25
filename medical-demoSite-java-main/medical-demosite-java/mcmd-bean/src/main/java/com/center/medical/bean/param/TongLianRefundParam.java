package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 通联支付 退款参数
 */
@Data
public class TongLianRefundParam implements Serializable {
    private static final long serialVersionUID = -8998593823777841601L;

    @ApiModelProperty(value = "退款金额,单位为分")
    private long trxamt;

    @ApiModelProperty(value = "商户退款订单号")
    private String reqsn;

    @ApiModelProperty(value = "原交易订单号")
    private String oldtrxid;

    @ApiModelProperty(value = "原交易流水")
    private String oldreqsn;

    @ApiModelProperty(value = "返回数据")
    private Map<String, String> payResult;

    @ApiModelProperty(value = "类型：9通联支付、11通联支付2")
    private Integer type;

}
