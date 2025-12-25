package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 随行支付 退款参数
 */
@Data
public class SuiXingTradeRefundParam implements Serializable {
    private static final long serialVersionUID = -5929618913280567553L;

    @ApiModelProperty(value = "原交易商户订单号\n" +
            "只能包含字母、数字、下划线；需保证在商户端不重复\n" +
            "与origUuid，origSxfUuid字段三选一必传")
    private String origOrderNo;

    @ApiModelProperty(value = "原交易科技公司订单号\n" +
            "与origOrderNo，origSxfUuid字段三选一必传")
    private String origUuid;

    @ApiModelProperty(value = "正交易落单号\n" +
            "与origUuid，origOrderNo字段三选一必传")
    private String origSxfUuid;

    @ApiModelProperty(value = "订单总金额(元)，格式：#######.##")
    private String amt;

    @ApiModelProperty(value = "支付后返回数据")
    private Map<String, Object> payResult;

}
