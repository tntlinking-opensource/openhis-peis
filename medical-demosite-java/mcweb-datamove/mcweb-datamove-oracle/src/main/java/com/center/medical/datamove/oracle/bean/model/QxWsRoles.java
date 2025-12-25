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
 * (QxWsRoles)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:07
 */
@Data
@TableName("QX_WS_ROLES")
@ApiModel(value = "QxWsRoles", description = "$tableInfo.comment实体类")
public class QxWsRoles extends Model<QxWsRoles> implements Serializable {
    private static final long serialVersionUID = 568983083082019601L;

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

    @ApiModelProperty(value = "${column.comment}")
    private String roleType;
}
