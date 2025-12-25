package com.center.medical.reservation.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: 路飞
 * @date: 2023-03-28 15:48
 * @description: 获取团体可预约列表
 */
@Data
@ApiModel(value = "获取团体可预约列表", description = "获取团体可预约列表")
public class ReservationSettingGroupDto {

    @ApiModelProperty(value = "预约日期")
    private Date reservationDate;

    @ApiModelProperty(value = "预约类型")
    private Integer levelId;

    @ApiModelProperty(value = "预约类型名称")
    private String levelName;

    @ApiModelProperty(value = "分中心名称")
    private String fzx;

    @ApiModelProperty(value = "状态：0.关闭 1.开放 2.过期")
    private String status;

    @ApiModelProperty(value = "人数上限(上午)")
    private Integer maxNumAm;

    @ApiModelProperty(value = "剩余可预约人数(上午)")
    private Integer ableNumAm;

    @ApiModelProperty(value = "已预约人数(上午)")
    private Integer doneNumAm;

    @ApiModelProperty(value = "人数上限(下午)")
    private Integer maxNumPm;

    @ApiModelProperty(value = "剩余可预约人数(下午)")
    private Integer ableNumPm;

    @ApiModelProperty(value = "已预约人数(下午)")
    private Integer doneNumPm;

}
