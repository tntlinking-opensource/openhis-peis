package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * (AppVersion)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:11
 */
@Data
@TableName("APP_VERSION")
@ApiModel(value = "AppVersion", description = "$tableInfo.comment实体类")
public class AppVersion extends Model<AppVersion> implements Serializable {
    private static final long serialVersionUID = 801952121144789388L;

    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String vername;

    @ApiModelProperty(value = "${column.comment}")
    private String vercode;

    @ApiModelProperty(value = "${column.comment}")
    private String verinfo;

    @ApiModelProperty(value = "${column.comment}")
    private String url;

    @ApiModelProperty(value = "${column.comment}")
    private String updateflag;

    @ApiModelProperty(value = "${column.comment}")
    private Date createtime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer status;

    @ApiModelProperty(value = "${column.comment}")
    private String flg;
}
