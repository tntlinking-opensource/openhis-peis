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
 * (QxWsUsers)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:24:10
 */
@Data
@TableName("QX_WS_USERS")
@ApiModel(value = "QxWsUsers", description = "$tableInfo.comment实体类")
public class QxWsUsers extends Model<QxWsUsers> implements Serializable {
    private static final long serialVersionUID = 846725723685084557L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String username;

    @ApiModelProperty(value = "${column.comment}")
    private String password;

    @ApiModelProperty(value = "${column.comment}")
    private String name;

    @ApiModelProperty(value = "${column.comment}")
    private String email;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private Date logindate;

    @ApiModelProperty(value = "${column.comment}")
    private String loginip;

    @ApiModelProperty(value = "${column.comment}")
    private String useraccountnonexpired;

    @ApiModelProperty(value = "${column.comment}")
    private String useraccountnonlocked;

    @ApiModelProperty(value = "${column.comment}")
    private String userenabled;

    @ApiModelProperty(value = "${column.comment}")
    private String usercredentialsnonexpired;

    @ApiModelProperty(value = "${column.comment}")
    private String depid;

    @ApiModelProperty(value = "${column.comment}")
    private String issystem;

    @ApiModelProperty(value = "${column.comment}")
    private String isleader;

    @ApiModelProperty(value = "${column.comment}")
    private String cid;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String inputCode;

    @ApiModelProperty(value = "${column.comment}")
    private String phone;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDoc;

    @ApiModelProperty(value = "${column.comment}")
    private String customerId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer ishelp;
}
