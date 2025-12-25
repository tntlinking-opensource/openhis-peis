package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检状态统计 分页返回数据
 */
@Data
public class ExaminerStatusVo implements Serializable {
    private static final long serialVersionUID = 3506555330989701431L;

    @Excel(name="登记日期",dateFormat="YYYY-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "date0")
    private Date date0;

    @Excel(name = "星期")
    @ApiModelProperty(value = "date1")
    private int date1;

    @Excel(name = "几天前")
    @ApiModelProperty(value = "date2")
    private int date2;

    @Excel(name = "已登记")
    @ApiModelProperty(value = "date3")
    private String date3;

    @Excel(name = "已缴费")
    @ApiModelProperty(value = "date4")
    private String date4;

    @Excel(name = "已分检")
    @ApiModelProperty(value = "date5")
    private String date5;

    @Excel(name = "已总检")
    @ApiModelProperty(value = "date6")
    private String date6;

    @Excel(name = "已打印")
    @ApiModelProperty(value = "date7")
    private String date7;

    @Excel(name = "已领取")
    @ApiModelProperty(value = "date8")
    private String date8;

    @Excel(name = "已结案")
    @ApiModelProperty(value = "date9")
    private String date9;

    @Excel(name = "已归档")
    @ApiModelProperty(value = "date10")
    private String date10;

    @ApiModelProperty(value = "登记时间")
    private String dateregister;

}
