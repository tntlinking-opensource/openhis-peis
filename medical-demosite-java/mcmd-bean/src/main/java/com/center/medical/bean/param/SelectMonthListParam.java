package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *销售目标自动统计月度计划  参数
 */
@Data
public class SelectMonthListParam implements Serializable {
    private static final long serialVersionUID = -5947739205672352536L;

    @ApiModelProperty(value = "年-开始时间")
    private String yearStart;

    @ApiModelProperty(value = "年-结束")
    private String yearEnd;

    @ApiModelProperty(value = "一月开始时间")
    private String monthStart1;

    @ApiModelProperty(value = "一月结束时间")
    private String monthEnd1;

    @ApiModelProperty(value = "二月开始时间")
    private String monthStart2;

    @ApiModelProperty(value = "二月结束时间")
    private String monthEnd2;


    @ApiModelProperty(value = "三月开始时间")
    private String monthStart3;

    @ApiModelProperty(value = "三月结束时间")
    private String monthEnd3;

    @ApiModelProperty(value = "四月开始时间")
    private String monthStart4;

    @ApiModelProperty(value = "四月结束时间")
    private String monthEnd4;


    @ApiModelProperty(value = "五月开始时间")
    private String monthStart5;

    @ApiModelProperty(value = "五月结束时间")
    private String monthEnd5;


    @ApiModelProperty(value = "六月开始时间")
    private String monthStart6;

    @ApiModelProperty(value = "六月结束时间")
    private String monthEnd6;


    @ApiModelProperty(value = "七月开始时间")
    private String monthStart7;

    @ApiModelProperty(value = "七月结束时间")
    private String monthEnd7;


    @ApiModelProperty(value = "八月开始时间")
    private String monthStart8;

    @ApiModelProperty(value = "八月结束时间")
    private String monthEnd8;

    @ApiModelProperty(value = "九月开始时间")
    private String monthStart9;

    @ApiModelProperty(value = "九月结束时间")
    private String monthEnd9;



    @ApiModelProperty(value = "十月开始时间")
    private String monthStart10;

    @ApiModelProperty(value = "十月结束时间")
    private String monthEnd10;


    @ApiModelProperty(value = "十一月开始时间")
    private String monthStart11;

    @ApiModelProperty(value = "十一月结束时间")
    private String monthEnd11;


    @ApiModelProperty(value = "十二月开始时间")
    private String monthStart12;

    @ApiModelProperty(value = "十二月结束时间")
    private String monthEnd12;
}
