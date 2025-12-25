package com.center.medical.reservation.bean.dto;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 团体预约记录
 *
 * @author makejava
 * @since 2023-08-31 16:45:54
 */
@Data
@ApiModel(value = "ReservationGroupData", description = "团体预约记录")
public class ReservationGroupData {

    @ApiModelProperty(value = "分中心")
    @Excel(name = "分中心")
    private String fzx;

    @ApiModelProperty(value = "预约类型")
    @Excel(name = "预约类型")
    private String levelName;

    @ApiModelProperty(value = "订单号")
    @Excel(name = "订单号")
    private String orderNum;

    @ApiModelProperty(value = "订单名称")
    @Excel(name = "订单名称")
    private String orderName;

    @ApiModelProperty(value = "销售经理")
    @Excel(name = "销售经理")
    private String xsjlName;

    @ApiModelProperty(value = "预约人")
    @Excel(name = "预约人")
    private String creator;

//    @ApiModelProperty(value = "套餐ID")
//    @Excel(name = "套餐ID")
//    private String comboId;

//    @ApiModelProperty(value = "套餐名称")
//    @Excel(name = "套餐名称")
//    private String tjtcmc;

    @ApiModelProperty(value = "预约人数(上午)")
    @Excel(name = "预约人数(上午)")
    private Integer countAm;

    @ApiModelProperty(value = "来捡人数(上午)")
    @Excel(name = "已来捡人数(上午)")
    private Integer finishedAm;

    @ApiModelProperty(value = "预约人数(下午)")
    @Excel(name = "预约人数(下午)")
    private Integer countPm;

    @ApiModelProperty(value = "已来捡人数(下午)")
    @Excel(name = "已来捡人数(下午)")
    private Integer finishedPm;

    @ApiModelProperty(value = "预约时间")
    @Excel(name = "预约时间",dateFormat="yyyy-MM-dd")
    private Date reservationDate;

    @ApiModelProperty(value = "备注")
    @Excel(name = "备注",dateFormat="yyyy-MM-dd")
    private String remark;
}
