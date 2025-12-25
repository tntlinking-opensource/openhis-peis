package com.center.medical.datamove.common.bean.model;


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
 * 网站资源(WsResource)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:41
 */
@Data
@TableName("ws_resource")
@ApiModel(value = "WsResource", description = "网站资源实体类")
public class WsResource extends Model<WsResource> implements Serializable {
    private static final long serialVersionUID = 855138665248491611L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String resourcename;

    @ApiModelProperty(value = "路径")
    private String resourceurl;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "模块名称")
    private String modulename;

    @ApiModelProperty(value = "模块id")
    private String moduleid;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "图标")
    private String imgvalue;

    @ApiModelProperty(value = "父级路径")
    private String presource;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "排序")
    private Double sortorder;

    @ApiModelProperty(value = "是否系统内置")
    private String issystem;

    @ApiModelProperty(value = "资源类型 url||method")
    private String type;

    @ApiModelProperty(value = "分类")
    private String classify;
}
