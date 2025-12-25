package com.center.medical.reception.bean.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TestGrid implements Serializable {

    private static final long serialVersionUID = 1424818226984377869L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "报告结果")
    private String reportRange;

    @ApiModelProperty(value = "报告范围")
    private String examitemvaluesreport;

    @ApiModelProperty(value = "艾迪康代码")
    private String adiconCode;
}
