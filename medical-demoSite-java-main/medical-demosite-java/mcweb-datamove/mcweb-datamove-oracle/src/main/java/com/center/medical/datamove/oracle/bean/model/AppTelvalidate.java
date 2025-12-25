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
 * (AppTelvalidate)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:01
 */
@Data
@TableName("APP_TELVALIDATE")
@ApiModel(value = "AppTelvalidate", description = "$tableInfo.comment实体类")
public class AppTelvalidate extends Model<AppTelvalidate> implements Serializable {
    private static final long serialVersionUID = -67894140001118890L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private String tel;

    @ApiModelProperty(value = "${column.comment}")
    private String yzm;

    @ApiModelProperty(value = "${column.comment}")
    private Date sxsj;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;
}
