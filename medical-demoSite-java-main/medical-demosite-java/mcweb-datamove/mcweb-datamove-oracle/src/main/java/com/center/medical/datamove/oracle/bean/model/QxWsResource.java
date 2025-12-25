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
 * (QxWsResource)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:04
 */
@Data
@TableName("QX_WS_RESOURCE")
@ApiModel(value = "QxWsResource", description = "$tableInfo.comment实体类")
public class QxWsResource extends Model<QxWsResource> implements Serializable {
    private static final long serialVersionUID = -19732651661715949L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String resourcename;

    @ApiModelProperty(value = "${column.comment}")
    private String resourceurl;

    @ApiModelProperty(value = "${column.comment}")
    private String description;

    @ApiModelProperty(value = "${column.comment}")
    private String modulename;

    @ApiModelProperty(value = "${column.comment}")
    private String moduleid;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String imgvalue;

    @ApiModelProperty(value = "${column.comment}")
    private String presource;

    @ApiModelProperty(value = "${column.comment}")
    private String state;

    @ApiModelProperty(value = "${column.comment}")
    private Double sortorder;

    @ApiModelProperty(value = "${column.comment}")
    private String issystem;

    @ApiModelProperty(value = "${column.comment}")
    private String type;

    @ApiModelProperty(value = "${column.comment}")
    private String classify;
}
