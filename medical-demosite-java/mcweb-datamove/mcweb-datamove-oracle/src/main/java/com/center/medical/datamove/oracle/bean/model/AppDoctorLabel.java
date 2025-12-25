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
 * 微信小程序医生标签关联表(AppDoctorLabel)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:32
 */
@Data
@TableName("APP_DOCTOR_LABEL")
@ApiModel(value = "AppDoctorLabel", description = "微信小程序医生标签关联表实体类")
public class AppDoctorLabel extends Model<AppDoctorLabel> implements Serializable {
    private static final long serialVersionUID = 440703392343881445L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String labelId;

    @ApiModelProperty(value = "${column.comment}")
    private String doctorId;
}
