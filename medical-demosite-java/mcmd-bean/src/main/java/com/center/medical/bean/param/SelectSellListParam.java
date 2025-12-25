package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 销售目标自动统计季度计划 参数
 */
@Data
public class SelectSellListParam implements Serializable {
    private static final long serialVersionUID = 2178079973287791933L;

    @ApiModelProperty(value = "年-开始时间")
    private String yearStart;

    @ApiModelProperty(value = "年-结束")
    private String yearEnd;

    @ApiModelProperty(value = "第一季度开始时间")
    private String seasonStart1;

    @ApiModelProperty(value = "第一季度结束时间")
    private String seasonEnd1;


    @ApiModelProperty(value = "第二季度开始时间")
    private String seasonStart2;

    @ApiModelProperty(value = "第二季度结束时间")
    private String seasonEnd2;


    @ApiModelProperty(value = "第三季度开始时间")
    private String seasonStart3;

    @ApiModelProperty(value = "第三季度结束时间")
    private String seasonEnd3;


    @ApiModelProperty(value = "第四季度开始时间")
    private String seasonStart4;

    @ApiModelProperty(value = "第四季度结束时间")
    private String seasonEnd4;
}
