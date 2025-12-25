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
 * APP常见问题(AppFaq)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:35
 */
@Data
@TableName("APP_FAQ")
@ApiModel(value = "AppFaq", description = "APP常见问题实体类")
public class AppFaq extends Model<AppFaq> implements Serializable {
    private static final long serialVersionUID = 272299670241335179L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String title;

    @ApiModelProperty(value = "${column.comment}")
    private String content;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String idx;
}
