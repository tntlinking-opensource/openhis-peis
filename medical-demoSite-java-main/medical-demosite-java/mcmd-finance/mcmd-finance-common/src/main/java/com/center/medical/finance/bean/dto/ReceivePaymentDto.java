package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取收款数据
 */
@Data
public class ReceivePaymentDto implements Serializable {
    private static final long serialVersionUID = -7696831183524373327L;

    @ApiModelProperty(value = "金蝶主键值")
    private String fid;

    @ApiModelProperty(value = "对方户名")
    private String remitter;

    @ApiModelProperty(value = "优惠金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "销售经理，多个用逗号隔开")
    private String xsjl;

    @ApiModelProperty(value = "结算方式的ID")
    private String idRemittanceway;

    @ApiModelProperty(value = "付款方式名称")
    private String paywayName;
}
