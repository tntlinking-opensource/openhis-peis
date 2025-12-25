package com.center.medical.enterprise.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取订单列表 参数
 */
@Data
public class GetOrderListParam implements Serializable {
    private static final long serialVersionUID = -6970135004408171503L;


    @ApiModelProperty(value = "单位id,不用传")
    private String customerId;
}
