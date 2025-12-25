package com.center.medical.abteilung.bean.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class JlcGridFaircheck implements Serializable {
    private static final long serialVersionUID = -3810915152418804662L;


    @ApiModelProperty(value = "体检类型，xy血液，bmi体重，yyzk营养状况")
    private String examType;


    @ApiModelProperty(value = "体征词")
    private String tzc;


    @ApiModelProperty(value = "体征词id")
    private String tzcId;


    @ApiModelProperty(value = "结论词Id")
    private String jlcId;
}
