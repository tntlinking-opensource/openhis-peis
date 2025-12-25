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
 * 微信小程序医生标签(AppLabel)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:41
 */
@Data
@TableName("APP_LABEL")
@ApiModel(value = "AppLabel", description = "微信小程序医生标签实体类")
public class AppLabel extends Model<AppLabel> implements Serializable {
    private static final long serialVersionUID = -95193900375920053L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String label;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String idx;
}
