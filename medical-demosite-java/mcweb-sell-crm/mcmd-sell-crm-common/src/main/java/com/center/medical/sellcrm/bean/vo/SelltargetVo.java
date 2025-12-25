package com.center.medical.sellcrm.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SelltargetVo implements Serializable {

    private static final long serialVersionUID = -1381178686189829358L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "用户id")
    private String userid;

    @Excel(name = "用户名字")
    @ApiModelProperty(value = "用户名字")
    private String username;

    @Excel(name = "第一季度目标额")
    @ApiModelProperty(value = "第一季度目标额")
    private String target1;

    @Excel(name = "第二季度目标额")
    @ApiModelProperty(value = "第二季度目标额")
    private String target2;

    @Excel(name = "第三季度目标额")
    @ApiModelProperty(value = "第三季度目标额")
    private String target3;

    @Excel(name = "第四季度目标额")
    @ApiModelProperty(value = "第四季度目标额")
    private String target4;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String bz;

    @Excel(name = "第一季度完成额")
    @ApiModelProperty(value = "第一季度完成额")
    private String complete1;

    @Excel(name = "第二季度完成额")
    @ApiModelProperty(value = "第二季度完成额")
    private String complete2;

    @Excel(name = "第三季度完成额")
    @ApiModelProperty(value = "第三季度完成额")
    private String complete3;

    @Excel(name = "第四季度完成额")
    @ApiModelProperty(value = "第四季度完成额")
    private String complete4;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "工作年龄")
    @ApiModelProperty(value = "工作年龄")
    private String workingAge;
}
