package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2023/11/29 17:32
 * @description: 订单审核流结果参数
 */
@Data
public class OrderFlowParam implements Serializable {
    private static final long serialVersionUID = -8633911858184611303L;

    @ApiModelProperty(value = "变更标志")
    private String fchange;

    @ApiModelProperty(value = "待审核的订单id")
    private String orderCheckId;

    @ApiModelProperty(value = "获取审批结果,0通过1不通过")
    private String spjgValue;

    @ApiModelProperty(value = "获取审批意见")
    private String spyjValue;

    public OrderFlowParam(String fchange, String orderCheckId, String spjgValue, String spyjValue) {
        this.fchange = fchange;
        this.orderCheckId = orderCheckId;
        this.spjgValue = spjgValue;
        this.spyjValue = spyjValue;
    }
}
