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
 * 微信小程序医生分中心关联表(AppDoctorBranch)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:31
 */
@Data
@TableName("APP_DOCTOR_BRANCH")
@ApiModel(value = "AppDoctorBranch", description = "微信小程序医生分中心关联表实体类")
public class AppDoctorBranch extends Model<AppDoctorBranch> implements Serializable {
    private static final long serialVersionUID = -16818081631805945L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorId;

    @ApiModelProperty(value = "${column.comment}")
    private String branchId;
}
