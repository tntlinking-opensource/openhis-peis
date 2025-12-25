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
 * (QxUsers)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:56
 */
@Data
@TableName("QX_USERS")
@ApiModel(value = "QxUsers", description = "$tableInfo.comment实体类")
public class QxUsers extends Model<QxUsers> implements Serializable {
    private static final long serialVersionUID = 866806998097446176L;

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
    private Double sdiscount;

    @ApiModelProperty(value = "${column.comment}")
    private Double ldiscount;

    @ApiModelProperty(value = "${column.comment}")
    private String isleader;

    @ApiModelProperty(value = "分中心id")
    private String cid;

    @ApiModelProperty(value = "是否删除:0 未删除1 删除")
    private Integer isDelete;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "交接密码")
    private String reciveCode;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDoc;

    @ApiModelProperty(value = "接口库用户代码")
    private String userCode;

    @ApiModelProperty(value = "医生照片")
    private String picture;

    @ApiModelProperty(value = "医生签字")
    private String signPic;

    @ApiModelProperty(value = "${column.comment}")
    private String superiorId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isbj;

    @ApiModelProperty(value = "${column.comment}")
    private String secretPassword;

    @ApiModelProperty(value = "${column.comment}")
    private Date expDate;

    @ApiModelProperty(value = "${column.comment}")
    private Date lockdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date unlockdate;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isLocked;

    @ApiModelProperty(value = "${column.comment}")
    private String kingdeeAccountNo;

    @ApiModelProperty(value = "${column.comment}")
    private String kingdeeUseStatus;

    @ApiModelProperty(value = "${column.comment}")
    private Date entryDate;
}
