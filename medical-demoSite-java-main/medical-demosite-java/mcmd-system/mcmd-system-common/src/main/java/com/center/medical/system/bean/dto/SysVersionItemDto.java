package com.center.medical.system.bean.dto;

import com.center.medical.common.annotation.Excel;
import com.center.medical.common.xss.Xss;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞船长
 * @date: 2024/4/16 15:09
 * @description: 版本更新记录
 */
@Data
public class SysVersionItemDto implements Serializable {
    private static final long serialVersionUID = -1527593792340613116L;

    @ApiModelProperty(value = "版本ID")
    @Excel(name = "版本ID")
    private Integer versionId;

    @ApiModelProperty(value = "更新类型，如[1,2,3]：1.前端 2.后端 3.数据库")
    @Excel(name = "更新类型")
    private Integer sysType;

    @ApiModelProperty(value = "修改模式：1.新增 2.优化")
    @Excel(name = "修改模式")
    private Integer modifyType;

    @ApiModelProperty(value = "模块名称")
    @Xss(message = "模块名称不能包含脚本字符")
    @Excel(name = "模块名称")
    private String moduleName;

    @ApiModelProperty(value = "图片(最多3张)")
    @Xss(message = "图片不能包含脚本字符")
    @Excel(name = "图片")
    private String pics;

    @ApiModelProperty(value = "更新内容")
    @Xss(message = "更新内容不能包含脚本字符")
    @Excel(name = "更新内容")
    private String content;

    @ApiModelProperty(value = "优先级：1.重要且紧急 2.重要不紧急 3.紧急不重要 4.不重要不紧急")
    @Excel(name = "优先级")
    private Integer priority;

    @ApiModelProperty(value = "开发周期(天)")
    @Excel(name = "开发周期(天)")
    private Integer devCycle;

    @ApiModelProperty(value = "进度：0.需求确认中 1.开发中 2.测试中 3.已完成")
    @Excel(name = "进度")
    private Integer progress;

    @ApiModelProperty(value = "备注")
    @Xss(message = "备注不能包含脚本字符")
    @Excel(name = "备注")
    private String remark;

    @ApiModelProperty(value = "状态：0.正常 1.废弃")
    @Excel(name = "状态")
    private Integer status;

    @ApiModelProperty(value = "开发者")
    @Xss(message = "开发者不能包含脚本字符")
    @Excel(name = "开发者")
    private String developer;

    @ApiModelProperty(value = "开发时间")
    @Excel(name = "开发时间")
    private Date developTime;

    @ApiModelProperty(value = "测试者")
    @Xss(message = "测试者不能包含脚本字符")
    @Excel(name = "测试者")
    private String tester;

    @ApiModelProperty(value = "测试时间")
    @Excel(name = "测试时间")
    private Date testTime;

    @ApiModelProperty(value = "需求提出人ID(关联sys_user表user_no)")
    @Xss(message = "需求提出人ID不能包含脚本字符")
    @Excel(name = "需求提出人ID")
    private String proponentId;

    @ApiModelProperty(value = "需求提出人")
    @Xss(message = "需求提出人不能包含脚本字符")
    @Excel(name = "需求提出人")
    private String proponent;

    @ApiModelProperty(value = "提出时间")
    @Excel(name = "提出时间")
    private Date proposeTime;

    @ApiModelProperty(value = "需求确认人ID(关联sys_user表user_no)")
    @Xss(message = "需求确认人ID不能包含脚本字符")
    @Excel(name = "需求确认人ID")
    private String confirmerId;

    @ApiModelProperty(value = "需求确认人")
    @Xss(message = "需求确认人不能包含脚本字符")
    @Excel(name = "需求确认人")
    private String confirmer;

    @ApiModelProperty(value = "需求确认时间")
    @Xss(message = "不能包含脚本字符")
    @Excel(name = "")
    private Date comfirmTime;

    @ApiModelProperty(value = "验收人ID(关联sys_user表user_no)")
    @Xss(message = "验收人ID不能包含脚本字符")
    @Excel(name = "验收人ID")
    private Object accepterId;

    @ApiModelProperty(value = "验收人")
    @Xss(message = "验收人不能包含脚本字符")
    @Excel(name = "验收人")
    private String accepter;

    @ApiModelProperty(value = "验收时间")
    @Excel(name = "验收时间")
    private Date acceptTime;

    @ApiModelProperty(value = "创建者")
    @Xss(message = "创建者不能包含脚本字符")
    @Excel(name = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @Excel(name = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    @Xss(message = "更新者不能包含脚本字符")
    @Excel(name = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @Excel(name = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "完成时间")
    @Excel(name = "完成时间")
    private String finishTime;
}
