package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 普通预览科室报告 参数
 */
@Data
public class DiagnosticParam implements Serializable {
    private static final long serialVersionUID = -2964673386663735469L;

    @ApiModelProperty(value = "健康0 职业1")
    private Integer dh;

    @ApiModelProperty(value = "科室id")
    private String ksID;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "显示头部数据,0不显示，1显示")
    private Integer ShowHeaders;

    public DiagnosticParam(Integer dh, String ksID, String patientcode, Integer showHeaders) {
        this.dh = dh;
        this.ksID = ksID;
        this.patientcode = patientcode;
        this.ShowHeaders = showHeaders;
    }

    public DiagnosticParam() {
    }
}
