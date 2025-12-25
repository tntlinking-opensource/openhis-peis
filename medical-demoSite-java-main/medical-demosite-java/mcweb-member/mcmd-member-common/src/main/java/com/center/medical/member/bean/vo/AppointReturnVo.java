package com.center.medical.member.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data

public class AppointReturnVo implements Serializable {

    private static final long serialVersionUID = -623369729160667804L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "本次是否来检")
    private String isinspects;

    @ApiModelProperty(value = "本次预约时间")
    private Date inspectTimes;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "性别",readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private String idSex;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Excel(name = "公司")
    @ApiModelProperty(value = "公司")
    private String dw;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "首次预约时间")
    @ApiModelProperty(value = "首次预约时间")
    private Date inspectTime;

    @Excel(name = "首次回访人")
    @ApiModelProperty(value = "首次回访人")
    private String visitId;

    @Excel(name = "首次回访时间")
    @ApiModelProperty(value = "首次回访时间")
    private Date visitTime;

    @Excel(name = "首次回访备注")
    @ApiModelProperty(value = "首次回访备注")
    private String memo;


    @Excel(name = "本次回访人")
    @ApiModelProperty(value = "本次回访人")
    private String visitids;

    @Excel(name = "本次回访时间")
    @ApiModelProperty(value = "本次回访时间")
    private Date visitTimes;

    @Excel(name = "本次回访备注")
    @ApiModelProperty(value = "本次回访备注")
    private String memos;

}
