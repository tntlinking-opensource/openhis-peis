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
 * (QxRoles)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:50
 */
@Data
@TableName("QX_ROLES")
@ApiModel(value = "QxRoles", description = "$tableInfo.comment实体类")
public class QxRoles extends Model<QxRoles> implements Serializable {
    private static final long serialVersionUID = -30677852555352141L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String name;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String description;

    @ApiModelProperty(value = "${column.comment}")
    private String issystem;
}
