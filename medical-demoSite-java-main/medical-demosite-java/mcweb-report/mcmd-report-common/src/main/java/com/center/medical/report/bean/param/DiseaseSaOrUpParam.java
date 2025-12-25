package com.center.medical.report.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class DiseaseSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -8963577616710545593L;

    @ApiModelProperty(value = "体检号")
    private String patientno;

    @ApiModelProperty(value = "综述")
    private String summary;

    @ApiModelProperty(value = "结论")
    private String conclusion;

    @ApiModelProperty(value = "建议")
    private String advice;

    @ApiModelProperty(value = "阳性")
    private String posistive;

    @ApiModelProperty(value = "职业总检：健康建议")
    private String jkoffer;
}
