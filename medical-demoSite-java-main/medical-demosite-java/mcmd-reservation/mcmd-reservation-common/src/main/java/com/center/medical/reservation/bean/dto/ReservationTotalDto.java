package com.center.medical.reservation.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2023-03-28 15:48
 * @description: 预约详情统计
 */
@Data
@ApiModel(value = "预约详情统计", description = "预约详情统计")
public class ReservationTotalDto implements Serializable {
    private static final long serialVersionUID = 8436812544866039953L;

    @ApiModelProperty(value = "预约日期")
    @Excel(name = "预约日期",dateFormat="yyyy-MM-dd")
    private Date reservationDate;

    @ApiModelProperty(value = "星期")
    @Excel(name = "星期")
    private String weekName;

    @ApiModelProperty(value = "分中心名称")
    @Excel(name = "分中心名称")
    private String fzx;

    @ApiModelProperty(value = "普通_预约人数(上午)")
    @Excel(name = "普通_预约人数(上午)")
    private Integer count1Am;

    @ApiModelProperty(value = "")
    @Excel(name = "普通_上限(上午)")
    private Integer count1MaxAm;

    @ApiModelProperty(value = "普通_预约人数(下午)")
    @Excel(name = "普通_预约人数(下午)")
    private Integer count1Pm;

    @ApiModelProperty(value = "普通_上限(下午)")
    @Excel(name = "普通_上限(下午)")
    private Integer count1MaxPm;

    @ApiModelProperty(value = "vip_预约人数")
    @Excel(name = "vip_预约人数")
    private Integer count2;

    @ApiModelProperty(value = "vip_上限")
    @Excel(name = "vip_上限")
    private Integer count2Max;

    @ApiModelProperty(value = "vvip_预约人数")
    @Excel(name = "vvip_预约人数")
    private Integer count3;

    @ApiModelProperty(value = "vvip_上限")
    @Excel(name = "vvip_上限")
    private Integer count3Max;

    @ApiModelProperty(value = "其他_预约人数")
    @Excel(name = "其他_预约人数")
    private Integer count4;

    @ApiModelProperty(value = "其他_上限")
    @Excel(name = "其他_上限")
    private Integer count4Max;

//    public ReservationTotalDto(Date reservationDate, String weekName, String fzx, Integer count1Am, Integer count1MaxAm, Integer count1Pm, Integer count1MaxPm, Integer count2, Integer count2Max, Integer count3, Integer count3Max) {
//        this.reservationDate = reservationDate;
//        this.weekName = weekName;
//        this.fzx = fzx;
//        this.count1Am = count1Am;
//        this.count1MaxAm = count1MaxAm;
//        this.count1Pm = count1Pm;
//        this.count1MaxPm = count1MaxPm;
//        this.count2 = count2;
//        this.count2Max = count2Max;
//        this.count3 = count3;
//        this.count3Max = count3Max;
//    }
}
