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
 * 微信小程序用户问卷关联表(AppUserQuestionnaire)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:09
 */
@Data
@TableName("APP_USER_QUESTIONNAIRE")
@ApiModel(value = "AppUserQuestionnaire", description = "微信小程序用户问卷关联表实体类")
public class AppUserQuestionnaire extends Model<AppUserQuestionnaire> implements Serializable {
    private static final long serialVersionUID = -83715144865144653L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String appUserId;

    @ApiModelProperty(value = "${column.comment}")
    private String questionnaireId;

    @ApiModelProperty(value = "${column.comment}")
    private String familyId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isFamily;

    @ApiModelProperty(value = "${column.comment}")
    private String branchId;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorId;

    @ApiModelProperty(value = "${column.comment}")
    private String status;

    @ApiModelProperty(value = "${column.comment}")
    private Date checkTime;

    @ApiModelProperty(value = "${column.comment}")
    private Date payTime;

    @ApiModelProperty(value = "${column.comment}")
    private String price;

    @ApiModelProperty(value = "${column.comment}")
    private String modifier;

    @ApiModelProperty(value = "${column.comment}")
    private Date refundTime;

    @ApiModelProperty(value = "${column.comment}")
    private String refundUsername;
}
