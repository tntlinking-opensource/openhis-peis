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
 * 微信小程序医生(AppDoctor)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:29
 */
@Data
@TableName("APP_DOCTOR")
@ApiModel(value = "AppDoctor", description = "微信小程序医生实体类")
public class AppDoctor extends Model<AppDoctor> implements Serializable {
    private static final long serialVersionUID = -78399933010437382L;

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
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDisable;

    @ApiModelProperty(value = "${column.comment}")
    private String name;

    @ApiModelProperty(value = "${column.comment}")
    private String picture;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isConsult;

    @ApiModelProperty(value = "${column.comment}")
    private String consultPrice;

    @ApiModelProperty(value = "${column.comment}")
    private String idx;

    @ApiModelProperty(value = "${column.comment}")
    private String details;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isPublic;

    @ApiModelProperty(value = "${column.comment}")
    private String consultType;
}
