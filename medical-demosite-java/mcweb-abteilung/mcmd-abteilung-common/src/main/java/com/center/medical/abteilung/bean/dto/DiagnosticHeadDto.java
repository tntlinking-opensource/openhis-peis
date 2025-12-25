package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 普通预览科室报告 头数据
 */
@Data
public class DiagnosticHeadDto implements Serializable {
    private static final long serialVersionUID = -2754548860255186852L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private Integer idSex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

    @ApiModelProperty(value = "婚姻ID")
    private String idMarriage;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @ApiModelProperty(value = "照片")
    private String picture;

    @ApiModelProperty(value = "体检单位名称")
    private String workunit;

    @ApiModelProperty(value = "档案id")
    private String id;
}
