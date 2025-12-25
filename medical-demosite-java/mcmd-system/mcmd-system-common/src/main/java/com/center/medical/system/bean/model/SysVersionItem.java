package com.center.medical.system.bean.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统更新记录(SysVersionItem)表实体类
 *
 * @author makejava
 * @since 2024-03-01 18:02:37
 */
@Data
@TableName("sys_version_item")
@ApiModel(value = "SysVersionItem", description = "系统更新记录实体类")
public class SysVersionItem extends Model<SysVersionItem> implements Serializable {
    private static final long serialVersionUID = 279822419817602023L;

    @TableId(value = "log_id")
    @ApiModelProperty(value = "记录ID")
    private Integer logId;

    @ApiModelProperty(value = "版本ID")
    private Integer versionId;

    @ApiModelProperty(value = "更新类型，如[1,2,3]：1.前端 2.后端 3.数据库")
    private Integer sysType;

    @ApiModelProperty(value = "修改模式：1.新增 2.优化")
    private Integer modifyType;

    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    @ApiModelProperty(value = "图片(最多3张)")
    private String pics;

    @ApiModelProperty(value = "更新内容")
    private String content;

    @ApiModelProperty(value = "优先级：1.重要且紧急 2.重要不紧急 3.紧急不重要 4.不重要不紧急")
    private Integer priority;

    @ApiModelProperty(value = "开发周期(天)")
    private Integer devCycle;

    @ApiModelProperty(value = "进度：0.需求确认中 1.开发中 2.测试中 3.已完成")
    private Integer progress;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态：0.正常 1.废弃")
    private Integer status;

    @ApiModelProperty(value = "开发者")
    private String developer;

    @ApiModelProperty(value = "开发时间")
    private Date developTime;

    @ApiModelProperty(value = "测试者")
    private String tester;

    @ApiModelProperty(value = "测试时间")
    private Date testTime;

    @ApiModelProperty(value = "需求提出人ID(关联sys_user表user_no)")
    private String proponentId;

    @ApiModelProperty(value = "需求提出人")
    private String proponent;

    @ApiModelProperty(value = "提出时间")
    private Date proposeTime;

    @ApiModelProperty(value = "需求确认人ID(关联sys_user表user_no)")
    private String confirmerId;

    @ApiModelProperty(value = "需求确认人")
    private String confirmer;

    @ApiModelProperty(value = "需求确认时间")
    private Date comfirmTime;

    @ApiModelProperty(value = "验收人ID(关联sys_user表user_no)")
    private Object accepterId;

    @ApiModelProperty(value = "验收人")
    private String accepter;

    @ApiModelProperty(value = "验收时间")
    private Date acceptTime;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "完成时间")
    private Date finishTime;
}
