package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class TotalAuditVo implements Serializable {
    private static final long serialVersionUID = 4194694304156299950L;

    @ApiModelProperty(value = "姓名")
    private String xm;

    @ApiModelProperty(value = "数量")
    private String sl;

    @ApiModelProperty(value = "阶段")
    private String jd;
}
