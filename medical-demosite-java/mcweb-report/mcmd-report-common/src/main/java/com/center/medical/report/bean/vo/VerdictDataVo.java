package com.center.medical.report.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 科室小结 界面 数据
 */
@Data
public class VerdictDataVo implements Serializable {

    private static final long serialVersionUID = 432261622009827799L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "ver1")
    private String ver1;

    @ApiModelProperty(value = "ver2")
    private String ver2;

    @ApiModelProperty(value = "ver3")
    private String ver3;

    @ApiModelProperty(value = "ver4")
    private String ver4;

    @ApiModelProperty(value = "ver5")
    private String ver5;

    @ApiModelProperty(value = "ver6")
    private String ver6;

    @ApiModelProperty(value = "ver7")
    private String ver7;

    @ApiModelProperty(value = "ver8")
    private int ver8;

    @ApiModelProperty(value = "ver9")
    private String ver9;

}
