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
 * APP???(AppInfo)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:36
 */
@Data
@TableName("APP_INFO")
@ApiModel(value = "AppInfo", description = "APP???实体类")
public class AppInfo extends Model<AppInfo> implements Serializable {
    private static final long serialVersionUID = 848113453455652461L;

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
    private String infoSort;

    @ApiModelProperty(value = "${column.comment}")
    private String content;

    @ApiModelProperty(value = "${column.comment}")
    private String contentSave;

    @ApiModelProperty(value = "${column.comment}")
    private Integer infoState;

    @ApiModelProperty(value = "${column.comment}")
    private String typeId;

    @ApiModelProperty(value = "${column.comment}")
    private String cid;

    @ApiModelProperty(value = "${column.comment}")
    private String fzx;

    @ApiModelProperty(value = "${column.comment}")
    private String hits;

    @ApiModelProperty(value = "${column.comment}")
    private String username;

    @ApiModelProperty(value = "${column.comment}")
    private String picture;
}
