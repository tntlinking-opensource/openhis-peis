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
 * (QxWsUserRole)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:08
 */
@Data
@TableName("QX_WS_USER_ROLE")
@ApiModel(value = "QxWsUserRole", description = "$tableInfo.comment实体类")
public class QxWsUserRole extends Model<QxWsUserRole> implements Serializable {
    private static final long serialVersionUID = 740908988480386493L;

    @TableId(value = "USER_ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String userId;

    @ApiModelProperty(value = "${column.comment}")
    private String roleId;

    @ApiModelProperty(value = "${column.comment}")
    private String adminId;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String roleName;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;
}
