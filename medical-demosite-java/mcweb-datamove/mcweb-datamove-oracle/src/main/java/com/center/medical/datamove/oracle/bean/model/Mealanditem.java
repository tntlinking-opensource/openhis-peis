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
 * 普通套餐与收费项目关联表(Mealanditem)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:22:03
 */
@Data
@TableName("MEALANDITEM")
@ApiModel(value = "Mealanditem", description = "普通套餐与收费项目关联表实体类")
public class Mealanditem extends Model<Mealanditem> implements Serializable {
    private static final long serialVersionUID = -24599824566686131L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "套餐ID")
    private String tcid;

    @ApiModelProperty(value = "收费项目ID")
    private String sfxmid;

    @ApiModelProperty(value = "是否备选")
    private String sfbx;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isSystem;

    @ApiModelProperty(value = "优惠价")
    private Double price;

    @ApiModelProperty(value = "${column.comment}")
    private String itemSort;

    @ApiModelProperty(value = "${column.comment}")
    private String itemGroup;

    @ApiModelProperty(value = "${column.comment}")
    private Integer groupType;
}
