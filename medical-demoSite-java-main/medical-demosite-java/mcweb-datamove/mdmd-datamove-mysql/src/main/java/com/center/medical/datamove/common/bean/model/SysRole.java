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
 * 角色信息表(SysRole)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:37
 */
@Data
@TableName("sys_role")
@ApiModel(value = "SysRole", description = "角色信息表实体类")
public class SysRole extends Model<SysRole> implements Serializable {
    private static final long serialVersionUID = 692135675090519350L;

    @TableId(value = "role_id")
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色权限字符串")
    private String roleKey;

    @ApiModelProperty(value = "显示顺序")
    private Integer roleSort;

    @ApiModelProperty(value = "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    private String dataScope;

    @ApiModelProperty(value = "菜单树选择项是否关联显示")
    private Integer menuCheckStrictly;

    @ApiModelProperty(value = "部门树选择项是否关联显示")
    private Integer deptCheckStrictly;

    @ApiModelProperty(value = "角色状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "删除标志（0代表存在 2代表删除）")
    private String delFlag;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
