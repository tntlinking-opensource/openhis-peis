package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取检验科本届数据
 */
@Data
public class TestResultDto implements Serializable {
    private static final long serialVersionUID = 9213969292541948438L;

    @ApiModelProperty(value = "收费项目名称")
    private String p;

    @ApiModelProperty(value = "检查项目名称(冗余)（名称）")
    private String examitemNameR;

    @ApiModelProperty(value = "报告范围")
    private String pp;

    @ApiModelProperty(value = "状态")
    private String ps;

    @ApiModelProperty(value = "报告范围")
    private String mm;

    @ApiModelProperty(value = "状态")
    private String ms;
}
