package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FindJctjDto implements Serializable {
    private static final long serialVersionUID = 8070414083687114006L;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "部门编号，对应原体检系统部门id")
    private String deptNo;

    @ApiModelProperty(value = "结论ID")
    private String basconclusionId;

    @ApiModelProperty(value = "总检结论词名称 跟老系统的basconclusion是一个字段")
    private String basconclusionName;

    @ApiModelProperty(value = "检出总人数")
    private Integer c;

    @ApiModelProperty(value = "检出男性人数")
    private Integer d;

    @ApiModelProperty(value = "样本总人数")
    private Integer e;

    @ApiModelProperty(value = "样本男性人数")
    private Integer f;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "部门")
    private String orgDepart;

    @ApiModelProperty(value = "部门排序")
    private Integer reportSort;

    @ApiModelProperty(value = "查询标识")
    private String flag;
}
