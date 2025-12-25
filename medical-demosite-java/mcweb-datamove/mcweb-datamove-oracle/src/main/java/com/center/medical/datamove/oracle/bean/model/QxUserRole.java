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
 * (QxUserRole)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:54
 */
@Data
@TableName("QX_USER_ROLE")
@ApiModel(value = "QxUserRole", description = "$tableInfo.comment实体类")
public class QxUserRole extends Model<QxUserRole> implements Serializable {
    private static final long serialVersionUID = -98589728360697602L;

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
