package com.center.medical.datamove.common.bean.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）(SysDept)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:54:34
 */
@Data
@TableName("sys_dept")
@ApiModel(value = "SysDept", description = "沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）实体类")
public class SysDept extends Model<SysDept> implements Serializable {
    private static final long serialVersionUID = -80093174060344175L;

    @TableId(value = "dept_id")
    @ApiModelProperty(value = "部门id")
    private Long deptId;

    @ApiModelProperty(value = "部门编号")
    private String deptNo;

    @ApiModelProperty(value = "父部门id")
    private Long parentId;

    @ApiModelProperty(value = "祖级列表")
    private String ancestors;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "部门英文名")
    private String englishName;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "是否为功能部门0为非功能科室1为功能科室")
    private String isFunction;

    @ApiModelProperty(value = "拼音输入码")
    private String inputCode;

    @ApiModelProperty(value = "科室类型，详见：com.world.center.medical.enums.ksType")
    private String ksType;

    @ApiModelProperty(value = "数据报告格式，详见：com.world.center.bean.enums.SjbggsType")
    private Integer sjbggs;

    @ApiModelProperty(value = "检查地点")
    private String jcdd;

    @ApiModelProperty(value = "接口类型，详见：com.world.center.bean.enums.LpsJklxType")
    private String jklx;

    @ApiModelProperty(value = "科室号")
    private String ksh;

    @ApiModelProperty(value = "图片地址")
    private String imgpath;

    @ApiModelProperty(value = "健康报告默认模板存放路径")
    private String reportPathHealth;

    @ApiModelProperty(value = "职业报告默认模板存放路径")
    private String reportPathDisease;

    @ApiModelProperty(value = "是否最后追加图片：0否 1.是")
    private String addPicBefore;

    @ApiModelProperty(value = "科室报告排序")
    private Integer reportSort;

    @ApiModelProperty(value = "导引单排序")
    private Integer dydXh;

    @ApiModelProperty(value = "导引单备注")
    private String dydMemo;

    @ApiModelProperty(value = "金蝶号码")
    private String kingdeeAccountNo;

    @ApiModelProperty(value = "灰色图路径")
    private String greyImgpath;

    @ApiModelProperty(value = "是否在APP显示：0否 1.是")
    private String isShowapp;

    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    @ApiModelProperty(value = "负责人")
    private String leader;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "部门状态：0.正常 1.停用")
    private String status;

    @ApiModelProperty(value = "删除标志：0.代表存在 2.代表删除")
    private String delFlag;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

//    @ApiModelProperty(value = "表")
//    private String table;
//
//    @ApiModelProperty(value = "字段")
//    private String tem;
}
