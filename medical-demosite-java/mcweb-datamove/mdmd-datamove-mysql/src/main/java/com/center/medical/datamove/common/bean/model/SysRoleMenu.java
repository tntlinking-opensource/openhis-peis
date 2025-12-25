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
 * 角色和菜单关联表(SysRoleMenu)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:38
 */
@Data
@TableName("sys_role_menu")
@ApiModel(value = "SysRoleMenu", description = "角色和菜单关联表实体类")
public class SysRoleMenu extends Model<SysRoleMenu> implements Serializable {
    private static final long serialVersionUID = -52956873427470332L;

    @TableId(value = "role_id")
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;
}
