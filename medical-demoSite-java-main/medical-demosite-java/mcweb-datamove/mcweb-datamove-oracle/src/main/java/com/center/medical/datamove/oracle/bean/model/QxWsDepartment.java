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
 * (QxWsDepartment)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:01
 */
@Data
@TableName("QX_WS_DEPARTMENT")
@ApiModel(value = "QxWsDepartment", description = "$tableInfo.comment实体类")
public class QxWsDepartment extends Model<QxWsDepartment> implements Serializable {
    private static final long serialVersionUID = -60011742401640694L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String name;

    @ApiModelProperty(value = "${column.comment}")
    private String description;

    @ApiModelProperty(value = "${column.comment}")
    private String parent;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isFunction;

    @ApiModelProperty(value = "${column.comment}")
    private String inputCode;

    @ApiModelProperty(value = "${column.comment}")
    private String xh;

    @ApiModelProperty(value = "${column.comment}")
    private String sjbggs;

    @ApiModelProperty(value = "${column.comment}")
    private String jcdd;

    @ApiModelProperty(value = "${column.comment}")
    private String jklx;

    @ApiModelProperty(value = "${column.comment}")
    private String ksh;

    @ApiModelProperty(value = "${column.comment}")
    private String imgpath;

    @ApiModelProperty(value = "${column.comment}")
    private String reportPathHealth;

    @ApiModelProperty(value = "${column.comment}")
    private String reportPathDisease;

    @ApiModelProperty(value = "${column.comment}")
    private Integer addPicBefore;

    @ApiModelProperty(value = "${column.comment}")
    private String reportSort;

    @ApiModelProperty(value = "${column.comment}")
    private String dydXh;

    @ApiModelProperty(value = "${column.comment}")
    private String address;

    @ApiModelProperty(value = "${column.comment}")
    private String house;

    @ApiModelProperty(value = "${column.comment}")
    private String payment;

    @ApiModelProperty(value = "${column.comment}")
    private Date enddate;

    @ApiModelProperty(value = "${column.comment}")
    private String flag;

    @ApiModelProperty(value = "${column.comment}")
    private String flagId;

    @ApiModelProperty(value = "${column.comment}")
    private String dpcode;

    @ApiModelProperty(value = "${column.comment}")
    private String dplevel;

    @ApiModelProperty(value = "${column.comment}")
    private String customerId;
}
