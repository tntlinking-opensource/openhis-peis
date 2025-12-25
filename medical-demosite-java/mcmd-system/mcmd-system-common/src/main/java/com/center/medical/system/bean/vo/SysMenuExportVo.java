package com.center.medical.system.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 导出菜单列表
 */
@Data
public class SysMenuExportVo {

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID")
    @Excel(name = "菜单ID", cellType = Excel.ColumnType.NUMERIC, prompt = "菜单ID")
    private Long menuId;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    @Excel(name = "菜单名称", cellType = Excel.ColumnType.STRING, prompt = "菜单名称")
    private String menuName;

    /**
     * 父菜单名称
     */
    @ApiModelProperty(value = "父菜单名称")
    @Excel(name = "父菜单名称", cellType = Excel.ColumnType.STRING, prompt = "父菜单名称")
    private String parentName;

    /**
     * 显示状态（0显示 1隐藏）
     */
    @ApiModelProperty(value = "显示状态（0显示 1隐藏）")
    @Excel(name = "显示状态（0显示 1隐藏）", cellType = Excel.ColumnType.NUMERIC, prompt = "显示状态（0显示 1隐藏）")
    private String visible;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    @ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
    @Excel(name = "菜单状态（0显示 1隐藏）", cellType = Excel.ColumnType.NUMERIC, prompt = "菜单状态（0显示 1隐藏）")
    private String status;


    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址")
    @Excel(name = "路由地址", cellType = Excel.ColumnType.STRING, prompt = "路由地址")
    private String path;

    /**
     * 组件路径
     */
    @ApiModelProperty(value = "组件路径")
    @Excel(name = "组件路径", cellType = Excel.ColumnType.STRING, prompt = "组件路径")
    private String component;

    /**
     * 类型（M目录 C菜单 F按钮）
     */
    @ApiModelProperty(value = "类型（M目录 C菜单 F按钮）")
    @Excel(name = "类型（M目录 C菜单 F按钮）", cellType = Excel.ColumnType.NUMERIC, prompt = "类型（M目录 C菜单 F按钮）")
    private String menuType;

    /**
     * 权限字符串
     */
    @ApiModelProperty(value = "权限字符串")
    @Excel(name = "权限字符串", cellType = Excel.ColumnType.NUMERIC, prompt = "权限字符串")
    private String perms;
}
