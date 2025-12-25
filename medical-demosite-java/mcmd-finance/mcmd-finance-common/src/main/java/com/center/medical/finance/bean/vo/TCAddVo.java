package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 团体结算 返回数据
 */
@Data
public class TCAddVo implements Serializable {
    private static final long serialVersionUID = 263367907048508930L;


    @ApiModelProperty(value = "收费方式ID(弃用)")
    private String typeId;

    @ApiModelProperty(value = "备注(弃用)")
    private String memo;

    @ApiModelProperty(value = "实收金额(结算金额)")
    private Double realityMoney;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "团体ID")
    private String groupId;

    @ApiModelProperty(value = "应收金额 (弃用：应该是动态计算所有统收已收的金额)")
    private Double shouldMoney;

    @ApiModelProperty(value = "公司")
    private String orgName;


}
