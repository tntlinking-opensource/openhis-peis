package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 系统访问记录(SysLogininfor)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:35
 */
@Data
@TableName("sys_logininfor")
@ApiModel(value = "SysLogininfor", description = "系统访问记录实体类")
public class SysLogininfor extends Model<SysLogininfor> implements Serializable {
    private static final long serialVersionUID = -94134216314179399L;

    @TableId(value = "info_id")
    @ApiModelProperty(value = "访问ID")
    private Long infoId;

    @ApiModelProperty(value = "用户账号")
    private String userName;

    @ApiModelProperty(value = "登录IP地址")
    private String ipaddr;

    @ApiModelProperty(value = "登录地点")
    private String loginLocation;

    @ApiModelProperty(value = "浏览器类型")
    private String browser;

    @ApiModelProperty(value = "操作系统")
    private String os;

    @ApiModelProperty(value = "登录状态（0成功 1失败）")
    private String status;

    @ApiModelProperty(value = "提示消息")
    private String msg;

    @ApiModelProperty(value = "访问时间")
    private Date loginTime;
}
