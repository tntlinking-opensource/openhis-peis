package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 药房管理-药品管理分页返回数据
 */
@Data
public class DrugstoreDrugVo implements Serializable {
    private static final long serialVersionUID = -6823132829837198812L;


    @ApiModelProperty(value = "id")
    private String drugId;

    @ApiModelProperty(value = "药瓶分类（代号）")
    private String drugClass;

    @ApiModelProperty(value = "药品名称")
    private String drugName;

    @ApiModelProperty(value = "规格")
    private String drugStandard;

    @ApiModelProperty(value = "药品单价")
    private Double drugPrice;
}
