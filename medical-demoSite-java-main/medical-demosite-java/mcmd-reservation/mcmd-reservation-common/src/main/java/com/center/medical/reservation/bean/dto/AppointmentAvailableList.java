package com.center.medical.reservation.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-03-28 15:48
 * @description: 可预约时间段信息列表
 */
@Data
@ApiModel(value = "可预约时间段信息列表", description = "可预约时间段信息列表")
public class AppointmentAvailableList implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "时间段")
    private String timeStr;

    @ApiModelProperty(value = "可预约信息")
    private List<AppointmentAvailableDto> itemDto;
}
