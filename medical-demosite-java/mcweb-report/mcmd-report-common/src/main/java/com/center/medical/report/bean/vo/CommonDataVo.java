package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 总检管理-职业总检科室小结界面数据
 */
@Data
public class CommonDataVo implements Serializable {
    private static final long serialVersionUID = 2487800301907201013L;

    @ApiModelProperty(value = "co1")
    private String co1;

    @ApiModelProperty(value = "co2")
    private String co2;

    @ApiModelProperty(value = "co3")
    private String co3;

    @ApiModelProperty(value = "co4")
    private String co4;

    @ApiModelProperty(value = "co5")
    private String co5;

    @ApiModelProperty(value = "co6")
    private String co6;

    @ApiModelProperty(value = "co7")
    private String co7;

    @ApiModelProperty(value = "co8")
    private String co8;

    @ApiModelProperty(value = "co9")
    private String co9;

    @ApiModelProperty(value = "co10")
    private String co10;
}
