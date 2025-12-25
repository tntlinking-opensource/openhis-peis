package com.center.medical.report.bean.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class BgFormData implements Serializable {

    private static final long serialVersionUID = 2758058759145947809L;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "结论")
    private String verdict;

    @ApiModelProperty(value = "总检建议")
    private String offer;

    @ApiModelProperty(value = "处理意见")
    private String summary;

    @ApiModelProperty(value = "职业总检：健康建议")
    private String jkoffer;

    @ApiModelProperty(value = "否阳性结果(1阳性  0或NULL非阳性)")
    private String posistive;

    @ApiModelProperty(value = "职业报告结论词")
    private String reportConclusions;

    @ApiModelProperty(value = "zySummary（职业总检）")
    private String summaryId;

    @ApiModelProperty(value = "医生，多个用，分割")
    private String doctors;

}
