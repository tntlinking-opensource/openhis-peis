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
 * 小程序资讯点击记录(AppInfoHits)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:38
 */
@Data
@TableName("APP_INFO_HITS")
@ApiModel(value = "AppInfoHits", description = "小程序资讯点击记录实体类")
public class AppInfoHits extends Model<AppInfoHits> implements Serializable {
    private static final long serialVersionUID = 126836740579772223L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "创建时间，点击时间")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "小程序用户id   未登录时可为空")
    private String appUserId;

    @ApiModelProperty(value = "资讯id")
    private String appInfoId;
}
