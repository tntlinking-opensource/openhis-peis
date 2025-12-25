package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (DrugstoreDrug)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:18:29
 */
@Data
@TableName("DRUGSTORE_DRUG")
@ApiModel(value = "DrugstoreDrug", description = "$tableInfo.comment实体类")
public class DrugstoreDrug extends Model<DrugstoreDrug> implements Serializable {
    private static final long serialVersionUID = -40232686452998391L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "药瓶分类（代号）")
    private String drugClass;

    @ApiModelProperty(value = "药品名称")
    private String drugName;

    @ApiModelProperty(value = "规格")
    private String drugStandard;

    @ApiModelProperty(value = "药品单价")
    private Double drugPrice;

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
    private Double stock;

    @ApiModelProperty(value = "是否禁用")
    private Integer isForbidden;

    @ApiModelProperty(value = "拼音码")
    private String inputCode;
}
