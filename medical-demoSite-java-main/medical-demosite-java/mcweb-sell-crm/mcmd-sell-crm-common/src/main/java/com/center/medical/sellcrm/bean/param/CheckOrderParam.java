package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class CheckOrderParam implements Serializable {

    private static final long serialVersionUID = 5646508156093610803L;

    @ApiModelProperty(value = "变更标志")
    private String fchange;

    @ApiModelProperty(value = "待审核的订单id")
    private String orderCheckId;

    @ApiModelProperty(value = "获取审批结果,0通过1不通过")
    private String spjgValue;

    @ApiModelProperty(value = "获取审批意见")
    private String spyjValue;



}
