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
 * (BasMerge)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:19
 */
@Data
@TableName("BAS_MERGE")
@ApiModel(value = "BasMerge", description = "$tableInfo.comment实体类")
public class BasMerge extends Model<BasMerge> implements Serializable {
    private static final long serialVersionUID = -46228257926013092L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String mergeName;

    @ApiModelProperty(value = "${column.comment}")
    private String suggestion;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String inputCode;

    @ApiModelProperty(value = "${column.comment}")
    private String tjjy;

    @ApiModelProperty(value = "${column.comment}")
    private String creater;
}
