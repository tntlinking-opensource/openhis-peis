package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 职业团检报告 人员资质
 */
@Data
public class PersonAptitudeDto implements Serializable {
    private static final long serialVersionUID = 539470773339648356L;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "职务")
    private String post;

    @ApiModelProperty(value = "科室")
    private String ks;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "工作年限")
    private String workYears;

    @ApiModelProperty(value = "编号")
    private String number;
}
