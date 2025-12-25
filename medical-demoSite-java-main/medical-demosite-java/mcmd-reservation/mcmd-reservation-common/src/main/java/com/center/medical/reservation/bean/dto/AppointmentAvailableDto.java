package com.center.medical.reservation.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2023-03-28 15:48
 * @description: 可预约时间段信息
 */
@Data
@ApiModel(value = "可预约时间段信息", description = "可预约时间段信息")
public class AppointmentAvailableDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "预约日期")
    private Date reservationDate;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "时间段")
    private String timeStr;

    @ApiModelProperty(value = "人数上限")
    private Integer maxNum;

    @ApiModelProperty(value = "剩余可预约人数")
    private Integer ableNum;

    @ApiModelProperty(value = "已预约人数")
    private Integer doneNum;

    @ApiModelProperty(value = "预约类型")
    private Integer levelId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "状态：0.关闭 1.开放 2.过期")
    private Integer status;

    @ApiModelProperty(value = "分中心名称")
    private String branchName;

    @ApiModelProperty(value = "预约类型名称")
    private String levelName;

}
