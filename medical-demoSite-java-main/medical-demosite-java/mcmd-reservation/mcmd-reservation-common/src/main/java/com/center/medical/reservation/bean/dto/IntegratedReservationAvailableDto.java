package com.center.medical.reservation.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 可预约日期列表
 * @author xhp
 * @since 2024-01-11 8:46
 */
@Data
@ApiModel(value = "可预约日期列表", description = "可预约日期列表")
public class IntegratedReservationAvailableDto implements Serializable {
    @ApiModelProperty(value = "日期 yyyy-MM-dd")
    private String dateStr;

    @ApiModelProperty(value = "可预约信息")
    private List<AppointmentAvailableList> timeList;
}
