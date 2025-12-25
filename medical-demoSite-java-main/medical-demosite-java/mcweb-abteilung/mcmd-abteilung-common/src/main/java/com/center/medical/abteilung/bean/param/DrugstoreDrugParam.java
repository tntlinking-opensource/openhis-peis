package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 药房管理-药品管理分页参数
 */
@Data
public class DrugstoreDrugParam implements Serializable {
    private static final long serialVersionUID = 3396358254959716896L;

    @ApiModelProperty(value = "药瓶分类（代号）")
    private String drugClass;

    @ApiModelProperty(value = "药品名称")
    private String drugName;

    @ApiModelProperty(value = "规格")
    private String drugStandard;
}
