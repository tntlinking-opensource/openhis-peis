package com.center.medical.enterprise.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取订单信息 参数
 */
@Data
public class OrderListDataParam implements Serializable {
    private static final long serialVersionUID = -7415738487682789259L;

    @ApiModelProperty(value = "订单名称")
    private String key;

    @ApiModelProperty(value = "客户ID,不传")
    private String customerId;
}
