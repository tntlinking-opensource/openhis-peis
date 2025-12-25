package com.center.medical.system.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 沃德医疗部门总集(所有中心部门的总集)(SysDepartment)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-18 19:26:09
 */
@Data
@TableName("sys_department")
@ApiModel(value = "SysDepartment", description = "沃德医疗部门总集(所有中心部门的总集)实体类")
public class SysDepartment extends Model<SysDepartment> implements Serializable {
    private static final long serialVersionUID = -41068069822729434L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "部门id")
    private String id;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "父级部门ID")
    private String parent;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "是否已删除 0 未删除 1 已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "是否为功能部门0为非功能科室1为功能科室")
    private Integer isFunction;

    @ApiModelProperty(value = "拼音输入码")
    private String inputCode;

    @ApiModelProperty(value = "序号")
    private Integer xh;

    @ApiModelProperty(value = "数据报告格式")
    private Integer sjbggs;

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
    private Integer reportSort;

    @ApiModelProperty(value = "导引单排序")
    private Integer dydXh;

    @ApiModelProperty(value = "dyd_memo")
    private String dydMemo;

    @ApiModelProperty(value = "金蝶账号")
    private String kingdeeAccountNo;

    @ApiModelProperty(value = "灰图路径")
    private String greyImgpath;

    @ApiModelProperty(value = "是否在APP显示：0否 1是")
    private Integer isShowapp;
}
