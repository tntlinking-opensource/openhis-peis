package com.center.medical.reception.bean.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class JlcGrid implements Serializable {
    private static final long serialVersionUID = -2873366469499892563L;

    @ApiModelProperty(value = "结论词Id")
    private String jlcId;


    @ApiModelProperty(value = "PACS检查项目ID")
    private String verdictId;


    @ApiModelProperty(value = "PACS收费项目ID")
    private String chargesId;


}
