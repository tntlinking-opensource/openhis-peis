package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 完成finish参数
 */
@Data
public class FinishParam implements Serializable {

    private static final long serialVersionUID = 2110059782934999064L;

    @ApiModelProperty(value = "体检号")
    private String patientno;

    @ApiModelProperty(value = "综述")
    private String summary;

    @ApiModelProperty(value = "结论")
    private String conclusion;

    @ApiModelProperty(value = "建议")
    private String advice;


}
