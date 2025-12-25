package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单角标返回数据
 */
@Data
public class OrderMarkersDto implements Serializable {
    private static final long serialVersionUID = 4787261577768305292L;

    @ApiModelProperty(value = "待预约")
    private Integer pendingAppointment;

    @ApiModelProperty(value = "待体检")
    private Integer pendingTest;

}
