package com.center.medical.reservation.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 预约日期信息
 * @author: 路飞
 * @date: 2024-11-07 15:16:00
 * @description: 预约日期信息
 */
@Data
public class ReservationDateVo {

    @ApiModelProperty(value = "预约日期，格式：日期+00:00:00，如：2023-03-31 00:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reservationDate;

    @ApiModelProperty(value = "预约类型ID")
    private Integer levelId;

    @ApiModelProperty(value = "预约类型名称")
    private String levelName;

    @ApiModelProperty(value = "是否可约：0.不可约 1.可约")
    private Integer status;
}
