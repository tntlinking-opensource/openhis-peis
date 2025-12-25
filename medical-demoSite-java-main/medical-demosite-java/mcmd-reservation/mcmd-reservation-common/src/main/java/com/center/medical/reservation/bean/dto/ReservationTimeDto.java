package com.center.medical.reservation.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: 路飞
 * @date: 2024-12-18 15:48
 * @description: 合作第三方获取预约时间段
 */
@Data
public class ReservationTimeDto {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "预约日期")
    private Date reservationDate;

    @ApiModelProperty(value = "时间段")
    private String timeStr;

    @ApiModelProperty(value = "剩余可预约人数")
    private Integer ableNum;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;
}
