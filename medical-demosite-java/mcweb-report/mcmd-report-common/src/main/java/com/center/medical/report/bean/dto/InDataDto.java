package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 检验报告 数据dto
 */
@Data
public class InDataDto implements Serializable {
    private static final long serialVersionUID = -2889418034804414950L;

    @ApiModelProperty(value = "检查项目名称")
    private String examTotalName;

    @ApiModelProperty(value = "报告范围")
    private String examitemvaluesreport;

    @ApiModelProperty(value = "上下箭头")
    private String arrow;

    @ApiModelProperty(value = "参考范围（LIS范围）")
    private String refrange;

    @ApiModelProperty(value = "单位")
    private String units;


    public InDataDto() {
    }

    public InDataDto(String examTotalName, String examitemvaluesreport, String arrow, String refrange, String units) {
        this.examTotalName = examTotalName;
        this.examitemvaluesreport = examitemvaluesreport;
        this.arrow = arrow;
        this.refrange = refrange;
        this.units = units;
    }
}
