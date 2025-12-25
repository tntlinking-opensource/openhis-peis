package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 根据体检号获取报告数据
 * @author xhp
 * @since 2023-10-26 15:06
 */
@Data
public class ReportOpenApiGetByPatientcodeParam implements Serializable {
    @NotBlank
    @ApiModelProperty(value = "体检号")
    private String patientcode;
}
