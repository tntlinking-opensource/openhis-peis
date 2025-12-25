package com.center.medical.bean.event;

import com.center.medical.bean.param.OrderFlowParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: 路飞
 * @date: 2022-11-17 20:38
 * @description: 订单审核事件
 */
@Data
@AllArgsConstructor
public class OrderFlowEvent {

    @ApiModelProperty(value = "订单审核流结果参数")
    private OrderFlowParam param;
}
