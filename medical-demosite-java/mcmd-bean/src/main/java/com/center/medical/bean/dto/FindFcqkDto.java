package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FindFcqkDto implements Serializable {
    private static final long serialVersionUID = 645792476341351902L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

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

    @ApiModelProperty(value = "名称")
    private String name;
}
