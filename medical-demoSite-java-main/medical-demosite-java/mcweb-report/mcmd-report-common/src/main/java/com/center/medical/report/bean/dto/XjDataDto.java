package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 生成隐私小结，正常小结中不包含隐私项目
 */
@Data
public class XjDataDto implements Serializable {
    private static final long serialVersionUID = -2168490055601850607L;

    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitem;

    @ApiModelProperty(value = "检查项目名称(冗余)（名称）")
    private String examitemNameR;

    @ApiModelProperty(value = "报告范围")
    private String examitemvaluesreport;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "职业状态")
    private String zyStatus;

    @ApiModelProperty(value = "描述进小结：0或null.否 1.是")
    private Integer isDesc;

    @ApiModelProperty(value = "类型2")
    private String type2;

    @ApiModelProperty(value = "单位")
    private String units;

    @ApiModelProperty(value = "检查项目ID")
    private String idExamitem;

}
