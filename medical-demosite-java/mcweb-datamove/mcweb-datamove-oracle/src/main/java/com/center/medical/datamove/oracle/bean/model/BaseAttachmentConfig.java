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
 * (BaseAttachmentConfig)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:12:26
 */
@Data
@TableName("BASE_ATTACHMENT_CONFIG")
@ApiModel(value = "BaseAttachmentConfig", description = "$tableInfo.comment实体类")
public class BaseAttachmentConfig extends Model<BaseAttachmentConfig> implements Serializable {
    private static final long serialVersionUID = -74654948226186252L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "${column.comment}")
    private String aetitle;

    @ApiModelProperty(value = "${column.comment}")
    private String ip;

    @ApiModelProperty(value = "${column.comment}")
    private String port;

    @ApiModelProperty(value = "${column.comment}")
    private String realPath;

    @ApiModelProperty(value = "${column.comment}")
    private String visitPath;

    @ApiModelProperty(value = "${column.comment}")
    private String memo;

    @ApiModelProperty(value = "${column.comment}")
    private String memoA;

    @ApiModelProperty(value = "${column.comment}")
    private String memoB;

    @ApiModelProperty(value = "${column.comment}")
    private String flag;

    @ApiModelProperty(value = "${column.comment}")
    private String mappingPath;
}
