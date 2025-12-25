package com.center.medical.enterprise.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单详情 查询参数
 */
@Data
public class CreateOrderInfoDataParam implements Serializable {
    private static final long serialVersionUID = 7408764150774837583L;


    @ApiModelProperty(value = "单位id,不用传")
    private String customerId;
}
