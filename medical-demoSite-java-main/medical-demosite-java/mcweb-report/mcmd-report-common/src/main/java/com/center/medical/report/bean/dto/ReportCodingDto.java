package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 报告赋码存放数据
 */
@Data
public class ReportCodingDto implements Serializable {
    private static final long serialVersionUID = -1017223464528745038L;

    @ApiModelProperty(value = "体检号")
    private String patientCode;

    @ApiModelProperty(value = "地址")
    private String path;

    public ReportCodingDto(String patientCode, String path) {
        this.patientCode = patientCode;
        this.path = path;
    }

    public ReportCodingDto() {
    }
}
