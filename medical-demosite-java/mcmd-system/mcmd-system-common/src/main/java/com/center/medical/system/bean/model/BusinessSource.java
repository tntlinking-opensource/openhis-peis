package com.center.medical.system.bean.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 合作第三方(BusinessSource)表实体类
 *
 * @author 路飞船长
 * @since 2023-03-24 10:10:03
 */
@Data
@TableName("sys_business_source")
@ApiModel(value = "BusinessSource", description = "合作第三方实体类")
public class BusinessSource extends Model<BusinessSource> implements Serializable {
    private static final long serialVersionUID = 604537935840422252L;

    @TableId(value = "id")
    @ApiModelProperty(value = "记录ID")
    private Long id;

    @ApiModelProperty(value = "第三方ID")
    private String sourceId;

    @ApiModelProperty(value = "第三方名称")
    private String typeName;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "logo")
    private String logo;

    @ApiModelProperty(value = "配置设置")
    private String setting;

    @ApiModelProperty(value = "介绍")
    private String tip;

    @ApiModelProperty(value = "排序")
    private Integer seq;

    @ApiModelProperty(value = "状态：-1.删除 0.关闭 1.开放中")
    private Integer status;

    @ApiModelProperty(value = "记录时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
