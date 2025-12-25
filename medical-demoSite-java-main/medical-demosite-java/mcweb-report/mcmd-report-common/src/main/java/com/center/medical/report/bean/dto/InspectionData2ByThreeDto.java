package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通过三个体检号获取检验科结果
 */
@Data
public class InspectionData2ByThreeDto implements Serializable {
    private static final long serialVersionUID = -2636848179646924769L;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitem;

    @ApiModelProperty(value = "检查项目名称(冗余)（名称）")
    private String examitemNameR;

    @ApiModelProperty(value = "报告范围")
    private String examitemvaluesreportP;

    @ApiModelProperty(value = "状态")
    private String statusP;

    @ApiModelProperty(value = "报告范围")
    private String examitemvaluesreportS;

    @ApiModelProperty(value = "状态")
    private String statusS;

    @ApiModelProperty(value = "报告范围")
    private String examitemvaluesreportF;

    @ApiModelProperty(value = "状态")
    private String statusF;






}
