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
 * 沃德小程序公告(AppNotice)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:45
 */
@Data
@TableName("APP_NOTICE")
@ApiModel(value = "AppNotice", description = "沃德小程序公告实体类")
public class AppNotice extends Model<AppNotice> implements Serializable {
    private static final long serialVersionUID = 459514718167215774L;

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
    private Date publishTime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String creator;

    @ApiModelProperty(value = "${column.comment}")
    private String modifier;

    @ApiModelProperty(value = "${column.comment}")
    private String cid;
}
