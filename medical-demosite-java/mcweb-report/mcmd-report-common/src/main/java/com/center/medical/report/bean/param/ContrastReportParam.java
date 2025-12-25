package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 对比报告预览参数
 */
@Data
public class ContrastReportParam implements Serializable {
    private static final long serialVersionUID = -3871160035372945968L;

    @ApiModelProperty(value = "最近的体检号")
    private String patientcode;

    @ApiModelProperty(value = "上届体检号")
    private String patientcodeBefore;

    @ApiModelProperty(value = "第一个体检号（这是区分生成三个体检号和两个体检号报告的标识）")
    private String patientcodeFirst;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

}
