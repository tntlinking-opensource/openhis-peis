package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (QxWsRoleResource)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:05
 */
@Data
@TableName("QX_WS_ROLE_RESOURCE")
@ApiModel(value = "QxWsRoleResource", description = "$tableInfo.comment实体类")
public class QxWsRoleResource extends Model<QxWsRoleResource> implements Serializable {
    private static final long serialVersionUID = -76846754462360678L;

    @TableId(value = "ROLE_ID")
    @ApiModelProperty(value = "${column.comment}")
    private String roleId;

    @ApiModelProperty(value = "${column.comment}")
    private String resourceId;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;
}
