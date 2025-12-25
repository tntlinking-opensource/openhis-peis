package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 获取报告url 参数
 */
@Data
public class GetReportUrlParam implements Serializable {
    private static final long serialVersionUID = 4676327476952464610L;

    @NotNull(message = "dh不能为空!")
    @ApiModelProperty(value = "0健康，1职业")
    private String dh;


    @NotBlank(message = "体检号不能为空!")
    @ApiModelProperty(value = "体检号")
    private String patientcode;
}
