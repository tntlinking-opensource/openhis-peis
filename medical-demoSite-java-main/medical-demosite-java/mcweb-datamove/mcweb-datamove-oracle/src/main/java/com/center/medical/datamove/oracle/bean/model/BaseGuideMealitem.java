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
 * (BaseGuideMealitem)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:34
 */
@Data
@TableName("BASE_GUIDE_MEALITEM")
@ApiModel(value = "BaseGuideMealitem", description = "$tableInfo.comment实体类")
public class BaseGuideMealitem extends Model<BaseGuideMealitem> implements Serializable {
    private static final long serialVersionUID = 832904587722963312L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String mealId;

    @ApiModelProperty(value = "${column.comment}")
    private String itemId;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String ysPrice;

    @ApiModelProperty(value = "${column.comment}")
    private String price;

    @ApiModelProperty(value = "${column.comment}")
    private String remark;

    @ApiModelProperty(value = "${column.comment}")
    private String tj;

    @ApiModelProperty(value = "${column.comment}")
    private String orderindex;
}
