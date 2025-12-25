package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2023-03-28 13:43
 * @description: 体检系统可预约时间段
 */
@Data
public class AppointmentAvailableParam implements Serializable {

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "预约日期，格式：日期+00:00:00，如：2023-03-31 00:00:00")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reservationDate;


    @ApiModelProperty(value = "预约类型id")
    private Integer levelId;
}
