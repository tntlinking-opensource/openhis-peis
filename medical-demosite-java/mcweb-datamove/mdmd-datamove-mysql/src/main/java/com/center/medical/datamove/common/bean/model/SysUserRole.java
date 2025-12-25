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
 * 用户和角色关联表(SysUserRole)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:40
 */
@Data
@TableName("sys_user_role")
@ApiModel(value = "SysUserRole", description = "用户和角色关联表实体类")
public class SysUserRole extends Model<SysUserRole> implements Serializable {
    private static final long serialVersionUID = -80727711861869491L;

    @TableId(value = "user_id")
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;
}
