package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 报告内容 更新参数
 */
@Data
public class ReportContentUpParam implements Serializable {
    private static final long serialVersionUID = -8437124961191060475L;

    @ApiModelProperty(value = "内容")
    private Object content;

    @ApiModelProperty(value = "报告类型，0.检验报告 1.老人查体-分析报告 2.隐私报告 3.临时报告 4.团检报告 5.个检报告 6.对比报告")
    private Integer reportType;

    @ApiModelProperty(value = "样本id（团检报告查询使用）")
    private String analyzeId;

    @ApiModelProperty(value = "对比报告表ID(对比报告使用)")
    private String compareReportId;

    @ApiModelProperty(value = "体检类型,0.健康体检 1.职业体检")
    private String idExamtype;

    @ApiModelProperty(value = "体检号(个检报告查询使用等)")
    private String patientcode;

}
