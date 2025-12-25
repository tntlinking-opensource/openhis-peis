package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询报告内容 参数
 */
@Data
public class ReportContentParam implements Serializable {
    private static final long serialVersionUID = 455285933086418476L;

    @ApiModelProperty(value = "体检号(个检报告查询使用等)")
    private String patientcode;

    @ApiModelProperty(value = "体检类型,0.健康体检 1.职业体检 2.综合 3.复查")
    private String idExamtype;

    @ApiModelProperty(value = "报告类型，0.检验报告 1.老人查体-分析报告 2.隐私报告 3.临时报告 4.团检报告 5.个检报告 6.对比报告 7.Pacs报告 8.职业结果告知书 9.检验报告(除检验科的)")
    private Integer reportType;

    @ApiModelProperty(value = "样本id(团检报告查询使用)")
    private String analyzeId;

    @ApiModelProperty(value = "对比报告表ID(对比报告使用)")
    private String compareReportId;

    @ApiModelProperty(value = "科室id(Pacs报告查询使用)")
    private String ksId;


    public ReportContentParam(String patientcode, String idExamtype, Integer reportType, String analyzeId, String compareReportId, String ksId) {
        this.patientcode = patientcode;
        this.idExamtype = idExamtype;
        this.reportType = reportType;
        this.analyzeId = analyzeId;
        this.compareReportId = compareReportId;
        this.ksId = ksId;
    }


    public ReportContentParam() {
    }



}
