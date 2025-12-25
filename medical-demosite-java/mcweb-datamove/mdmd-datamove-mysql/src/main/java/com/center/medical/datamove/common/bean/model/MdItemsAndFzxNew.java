package com.center.medical.datamove.common.bean.model;


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
 * JC收费项目和分中心关联表(MdItemsAndFzxNew)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:46:23
 */
@Data
@TableName("md_items_and_fzx_new")
@ApiModel(value = "MdItemsAndFzxNew", description = "JC收费项目和分中心关联表实体类")
public class MdItemsAndFzxNew extends Model<MdItemsAndFzxNew> implements Serializable {
    private static final long serialVersionUID = -27133669806971022L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改时间")
    private Date modifydate;

    @ApiModelProperty(value = "收费项目ID")
    private String itemsId;

    @ApiModelProperty(value = "分中心ID")
    private String fzxId;

    @ApiModelProperty(value = "同步状态")
    private Integer tbzt;

    @ApiModelProperty(value = "禁止打折字段，1禁止打折")
    private Integer fDiscountdisabled;

    @ApiModelProperty(value = "成本价")
    private Double costprice;

    @ApiModelProperty(value = "套餐价格")
    private Double suiteprice;

    @ApiModelProperty(value = "价格")
    private Double unitprice;

    @ApiModelProperty(value = "内部价")
    private Double innerprice;

    @ApiModelProperty(value = "外部价")
    private Double coopprice;
}
