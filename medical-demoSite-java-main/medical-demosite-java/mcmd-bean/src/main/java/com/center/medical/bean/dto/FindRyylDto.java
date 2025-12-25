package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FindRyylDto implements Serializable {
    private static final long serialVersionUID = -4432714097179408119L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "否阳性结果(1阳性  0或NULL非阳性)")
    private String posistive;

    @ApiModelProperty(value = "处理意见")
    private String test;

    @ApiModelProperty(value = "序列号")
    private Integer serialNo;

    @ApiModelProperty(value = "结论(名称)")
    private String occupationSummary;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "接害工龄")
    private Integer jhgl;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "第三方名称")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;
}
