package com.center.medical.abteilung.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 药品基础表(DrugstoreDrug)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:51:58
 */
@Data
@TableName("md_drugstore_drug")
@ApiModel(value = "DrugstoreDrug", description = "药品基础表实体类")
public class DrugstoreDrug extends Model<DrugstoreDrug> implements Serializable {
    private static final long serialVersionUID = 160428292479276325L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
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
    private Integer stock;

    @ApiModelProperty(value = "是否禁用：0或null.否 1.是")
    private Integer isForbidden;

    @ApiModelProperty(value = "拼音码")
    private String inputCode;
}
