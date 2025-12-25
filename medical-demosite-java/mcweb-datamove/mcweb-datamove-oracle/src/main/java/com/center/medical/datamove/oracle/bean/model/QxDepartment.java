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
 * (QxDepartment)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:45
 */
@Data
@TableName("QX_DEPARTMENT")
@ApiModel(value = "QxDepartment", description = "$tableInfo.comment实体类")
public class QxDepartment extends Model<QxDepartment> implements Serializable {
    private static final long serialVersionUID = 378743372980524335L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "父级部门ID")
    private String parent;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "是否已删除 0 未删除 1 已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否为功能部门0为非功能科室1为功能科室 ")
    private Integer isFunction;

    @ApiModelProperty(value = "拼音输入码")
    private String inputCode;

    @ApiModelProperty(value = "序号")
    private String xh;

    @ApiModelProperty(value = "数据报告格式")
    private String sjbggs;

    @ApiModelProperty(value = "检查地点")
    private String jcdd;

    @ApiModelProperty(value = "接口类型")
    private String jklx;

    @ApiModelProperty(value = "科室号")
    private String ksh;

    @ApiModelProperty(value = "图片地址")
    private String imgpath;

    @ApiModelProperty(value = "健康报告默认模板存放路径")
    private String reportPathHealth;

    @ApiModelProperty(value = "职业报告默认模板存放路径")
    private String reportPathDisease;

    @ApiModelProperty(value = "是否最后追加图片")
    private Integer addPicBefore;

    @ApiModelProperty(value = "科室报告排序")
    private String reportSort;

    @ApiModelProperty(value = "导引单排序")
    private String dydXh;

    @ApiModelProperty(value = "${column.comment}")
    private String dydMemo;

    @ApiModelProperty(value = "${column.comment}")
    private String kingdeeAccountNo;

    @ApiModelProperty(value = "灰色图片")
    private String greyImgpath;

    @ApiModelProperty(value = "是否展示在APP里 0展示 1不展示")
    private Integer isShowapp;
}
