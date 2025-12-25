package com.center.medical.system.bean.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 路飞
 * @date: 2023-02-24 9:52
 * @description: 更新小项查询参数
 */
@Data
public class SysVersionItemParam {
    @ApiModelProperty(value = "版本ID")
    private Integer versionId;

    @ApiModelProperty(value = "更新类型，如[1,2,3]：1.前端 2.后端 3.数据库")
    private Integer sysType;

    @ApiModelProperty(value = "修改模式：1.新增 2.优化")
    private Integer modifyType;

    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    @ApiModelProperty(value = "优先级：1.重要且紧急 2.重要不紧急 3.紧急不重要 4.不重要不紧急")
    private Integer priority;

    @ApiModelProperty(value = "进度：0.需求确认中 1.开发中 2.测试中 3.已完成")
    private Integer progress;

    @ApiModelProperty(value = "状态：0.正常 1.废弃")
    private Integer status;

    @ApiModelProperty(value = "需求提出人")
    private String proponent;
}
