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
 * (AppArea)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:23
 */
@Data
@TableName("APP_AREA")
@ApiModel(value = "AppArea", description = "$tableInfo.comment实体类")
public class AppArea extends Model<AppArea> implements Serializable {
    private static final long serialVersionUID = -80981625967724678L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String areaName;

    @ApiModelProperty(value = "${column.comment}")
    private String pid;

    @ApiModelProperty(value = "${column.comment}")
    private String fullName;

    @ApiModelProperty(value = "${column.comment}")
    private String areaSort;

    @ApiModelProperty(value = "${column.comment}")
    private String stopFlag;

    @ApiModelProperty(value = "${column.comment}")
    private String levels;

    @ApiModelProperty(value = "${column.comment}")
    private String areaCode;

    @ApiModelProperty(value = "${column.comment}")
    private String areaDesc;

    @ApiModelProperty(value = "${column.comment}")
    private String latitude;

    @ApiModelProperty(value = "${column.comment}")
    private String longitude;
}
