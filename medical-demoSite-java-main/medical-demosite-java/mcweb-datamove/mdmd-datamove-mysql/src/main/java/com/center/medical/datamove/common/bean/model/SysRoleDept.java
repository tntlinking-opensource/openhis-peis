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
 * 角色和部门关联表(SysRoleDept)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:37
 */
@Data
@TableName("sys_role_dept")
@ApiModel(value = "SysRoleDept", description = "角色和部门关联表实体类")
public class SysRoleDept extends Model<SysRoleDept> implements Serializable {
    private static final long serialVersionUID = -12193912183891156L;

    @TableId(value = "role_id")
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;
}
