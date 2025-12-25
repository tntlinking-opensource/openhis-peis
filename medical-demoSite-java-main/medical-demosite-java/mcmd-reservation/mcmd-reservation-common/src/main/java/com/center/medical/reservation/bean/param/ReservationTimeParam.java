package com.center.medical.reservation.bean.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2023-03-28 13:43
 * @description: 体检系统可预约时间段
 */
@Data
public class ReservationTimeParam implements Serializable {

    private static final long serialVersionUID = 6028795074609443481L;

    @ApiModelProperty(value = "分中心ID", required = true)
    private String branchId;

    @ApiModelProperty(value = "预约时间")
    private String reservationTime;

    @ApiModelProperty(value = "预约日期，格式：日期+00:00:00，如：2023-03-31 00:00:00", required = true)
    private Date reservationDate;

    @ApiModelProperty(value = "预约类型id")
    private Integer levelId;
}
