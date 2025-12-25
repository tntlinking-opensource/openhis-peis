package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MyOrderDto implements Serializable {
    private static final long serialVersionUID = 5480689818923198342L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "预约号")
    private String reservationNo;

    @ApiModelProperty(value = "预约时间")
    private String reservationDate;

    @ApiModelProperty(value = "预约时间段")
    private String timeSlot;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "套餐名称")
    private String examsuiteName;

    @ApiModelProperty(value = "预约类型名称")
    private String levelName;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "状态 1待预约,2已预约,3体检中,4体检结束")
    private String status;

    @ApiModelProperty(value = "检查指标")
    private String inspectIndex;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "金额")
    private String moneyamount;

    @ApiModelProperty(value = "购买类型 0系统 1小程序")
    private String purchaseType;

    @ApiModelProperty(value = "业务编号")
    private String patientbizno;


    @ApiModelProperty(value = "是否已添满意度 0否1是")
    private Integer satisfactionLevel;
}
