package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 团体结算 结算页数据
 */
@Data
public class TCAddParam implements Serializable {
    private static final long serialVersionUID = -3284040752925595815L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "客户单位ID")
    private String khdwid;

    @ApiModelProperty(value = "应收金额 (弃用：应该是动态计算所有统收已收的金额)")
    private Double ys;

    @ApiModelProperty(value = "公司")
    private String orgName;

}
