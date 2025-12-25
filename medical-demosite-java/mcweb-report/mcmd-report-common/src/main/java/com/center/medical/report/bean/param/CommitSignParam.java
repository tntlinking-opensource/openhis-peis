package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommitSignParam implements Serializable {
    private static final long serialVersionUID = -651275557627039373L;

    @ApiModelProperty(value = "结论词id")
    private String conclusionId;

    @ApiModelProperty(value = "结论词名字")
    private String name;

    @ApiModelProperty(value = "建议")
    private String advice;

    @ApiModelProperty(value = "体检号")
    private String patientno;

    @ApiModelProperty(value = "健康0 职业1，就是老系统的flag")
    private String dh;

}
