package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FindJcqkhzDto implements Serializable {
    private static final long serialVersionUID = 7479788826456388341L;

    @ApiModelProperty(value = "职业体检类别(0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急)")
    private String regimentationNote;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;

    @ApiModelProperty(value = "危害因素ID")
    private String occupationDiagnosis;

    @ApiModelProperty(value = "团体部门")
    private String orgDepart;

    @ApiModelProperty(value = "yszyb")
    private int yszyb;

    @ApiModelProperty(value = "zyjjz")
    private int zyjjz;

    @ApiModelProperty(value = "fc")
    private int fc;

    @ApiModelProperty(value = "qtjb")
    private int qtjb;

    @ApiModelProperty(value = "wjyc")
    private int wjyc;

    @ApiModelProperty(value = "ashclass")
    private String ashclass;

    @ApiModelProperty(value = "hcclass")
    private String hcclass;
}
