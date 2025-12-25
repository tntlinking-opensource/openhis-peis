package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 药房管理-药品管理分页参数添加参数
 */
@Data
public class DrugstoresaOrUpParam implements Serializable {
    private static final long serialVersionUID = 6798318665922699601L;


    @ApiModelProperty(value = "id ,不传id就是添加，传id就是修改")
    private String id;

    @ApiModelProperty(value = "药品名称")
    private String drugName;

    @ApiModelProperty(value = "规格")
    private String drugStandard;

    @ApiModelProperty(value = "产地")
    private String drugPlace;

    @ApiModelProperty(value = "剂型")
    private String drugType;

    @ApiModelProperty(value = "单位")
    private String drugUnit;

    @ApiModelProperty(value = "零售价")
    private Double retailPrice;

    @ApiModelProperty(value = "成本价")
    private Double costPrice;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "药瓶分类（代号）")
    private String drugClass;
}
