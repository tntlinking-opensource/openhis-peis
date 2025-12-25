package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 随行支付 查询信息参数
 */
@Data
public class SuiXingTradeQueryParam implements Serializable {
    private static final long serialVersionUID = 1439832173762263500L;

    @ApiModelProperty(value = "商户订单号")
    private String ordNo;

    @ApiModelProperty(value = "科技公司订单号")
    private String uuid;

    @ApiModelProperty(value = "支付宝或微信单号")
    private String transactionId;

    @ApiModelProperty(value = "落单号")
    private String sxfUuid;

}
