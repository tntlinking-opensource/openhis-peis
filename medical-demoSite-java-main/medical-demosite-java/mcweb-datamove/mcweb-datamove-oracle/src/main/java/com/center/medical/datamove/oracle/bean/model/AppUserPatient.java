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
 * 小程序用户订单关联表(AppUserPatient)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:08
 */
@Data
@TableName("APP_USER_PATIENT")
@ApiModel(value = "AppUserPatient", description = "小程序用户订单关联表实体类")
public class AppUserPatient extends Model<AppUserPatient> implements Serializable {
    private static final long serialVersionUID = -94517401871898712L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String userId;

    @ApiModelProperty(value = "${column.comment}")
    private String patientcode;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isFamily;

    @ApiModelProperty(value = "${column.comment}")
    private String familyId;

    @ApiModelProperty(value = "${column.comment}")
    private Date buyTime;
}
