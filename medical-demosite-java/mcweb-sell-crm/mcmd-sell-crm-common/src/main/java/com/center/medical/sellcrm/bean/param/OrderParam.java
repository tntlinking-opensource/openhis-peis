package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2022/12/16 17:02
 * @description: 订单查询参数
 */
@Data
@ApiModel(value = "OrderParam", description = "订单查询参数")
public class OrderParam implements Serializable {
    private static final long serialVersionUID = -4776651863732077849L;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlId;

    @ApiModelProperty(value = "输入码")
    private String inputCode;
}
