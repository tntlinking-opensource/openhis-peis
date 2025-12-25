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
 * (QxUsersLock)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:57
 */
@Data
@TableName("QX_USERS_LOCK")
@ApiModel(value = "QxUsersLock", description = "$tableInfo.comment实体类")
public class QxUsersLock extends Model<QxUsersLock> implements Serializable {
    private static final long serialVersionUID = 770179151735538704L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String username;

    @ApiModelProperty(value = "${column.comment}")
    private String sessionId;

    @ApiModelProperty(value = "${column.comment}")
    private String failedcount;

    @ApiModelProperty(value = "${column.comment}")
    private Date lockdate;

    @ApiModelProperty(value = "${column.comment}")
    private String lockcount;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isLocked;

    @ApiModelProperty(value = "${column.comment}")
    private Date unlockdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;
}
