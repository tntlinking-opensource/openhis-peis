package com.center.medical.abteilung.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class GetSignParam implements Serializable {

    private static final long serialVersionUID = 2661004234381024071L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "检查项目类型: bmi体重指数、xy血压结论")
    private String examItemType;

    @ApiModelProperty(value = "检查结果")
    private Double examValue;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private Integer age;
}
