package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2023-03-21 14:46
 * @description: 更新预约设置信息查询参数
 */
@Data
@ApiModel(value = "更新预约设置信息查询参数")
public class ReservationSettingCondition implements Serializable {
    private static final long serialVersionUID = 1169294598091248873L;

    @ApiModelProperty(value = "预约日期")
    private Date reservationDate;

    @ApiModelProperty(value = "预约类型")
    private Integer levelId;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

    @ApiModelProperty(value = "预约人数(上午)", required = true)
    private Integer countAm;

    public ReservationSettingCondition(Date reservationDate, Integer levelId, String branchId, Integer countAm) {
        this.reservationDate = reservationDate;
        this.levelId = levelId;
        this.branchId = branchId;
        this.countAm = countAm;
    }
}
