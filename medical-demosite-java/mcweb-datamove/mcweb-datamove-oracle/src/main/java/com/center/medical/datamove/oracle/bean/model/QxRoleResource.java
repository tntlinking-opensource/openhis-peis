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
 * (QxRoleResource)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:49
 */
@Data
@TableName("QX_ROLE_RESOURCE")
@ApiModel(value = "QxRoleResource", description = "$tableInfo.comment实体类")
public class QxRoleResource extends Model<QxRoleResource> implements Serializable {
    private static final long serialVersionUID = 589491342958985001L;

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
