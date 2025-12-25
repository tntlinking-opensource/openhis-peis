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
 * 系统用户关联的科室(SysUserDep)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:40
 */
@Data
@TableName("sys_user_dep")
@ApiModel(value = "SysUserDep", description = "系统用户关联的科室实体类")
public class SysUserDep extends Model<SysUserDep> implements Serializable {
    private static final long serialVersionUID = 330868086078825377L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "科室ID")
    private String depId;

    @ApiModelProperty(value = "用户编号")
    private String userId;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    private Date modifydate;
}
