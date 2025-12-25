package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取复查名单
 */
@Data
public class ReviewNoticeListDto implements Serializable {
    private static final long serialVersionUID = -5000676225599619759L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "上岗前 在岗期间 离岗时")
    private String medicaltype;

    @ApiModelProperty(value = "接害工龄")
    private String jhgl;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "工种")
    private String trades;

    @ApiModelProperty(value = "接害因素")
    private String jhys;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

}
