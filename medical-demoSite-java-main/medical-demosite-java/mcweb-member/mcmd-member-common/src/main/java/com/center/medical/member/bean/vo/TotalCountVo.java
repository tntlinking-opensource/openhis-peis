package com.center.medical.member.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class TotalCountVo implements Serializable {
    private static final long serialVersionUID = -3711974765294310145L;

    @ApiModelProperty(value = "不满意")
    private Integer bmy;

    @ApiModelProperty(value = "非常满意")
    private Integer fcmy;

    @ApiModelProperty(value = "基本满意")
    private Integer jbmy;

    @ApiModelProperty(value = "满意")
    private Integer my;

    @ApiModelProperty(value = "满意度%")
    private Integer myd;

    @ApiModelProperty(value = "未评价")
    private Integer wpj;
}
