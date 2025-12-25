package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class HarmAndIllnessDto implements Serializable {
    private static final long serialVersionUID = -202371450437795646L;

    @ApiModelProperty(value = "职业病")
    private String industrialDisease;

    @ApiModelProperty(value = "禁忌症")
    private String contraindication;


}
