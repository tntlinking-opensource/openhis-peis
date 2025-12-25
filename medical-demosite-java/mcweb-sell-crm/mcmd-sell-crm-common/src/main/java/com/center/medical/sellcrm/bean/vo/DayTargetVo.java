package com.center.medical.sellcrm.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 销售日目标 返回数据
 */
@Data
public class DayTargetVo implements Serializable {
    private static final long serialVersionUID = 6104626223262416625L;

    @ApiModelProperty(value = "用户id")
    private String userid;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "销售经理")
    @ApiModelProperty(value = "用户名称")
    private String username;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "实际完成额")
    @ApiModelProperty(value = "实际完成额(合计)")
    private Double complete;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String bz;

    @Excel(name = "工龄")
    @ApiModelProperty(value = "工龄")
    private String workingAge;


}
