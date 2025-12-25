package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 团体结算保存 订单保存参数
 */
@Data
public class TCFormDataDto implements Serializable {
    private static final long serialVersionUID = -6963136950286372748L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "分组ID")
    private String groupId;

    @ApiModelProperty(value = "公司")
    private String orgName;

    @ApiModelProperty(value = "应收金额 (弃用：应该是动态计算所有统收已收的金额)")
    private Double shouldMoney;

    @ApiModelProperty(value = "实收金额(结算金额)")
    private Double realityMoney;

}
