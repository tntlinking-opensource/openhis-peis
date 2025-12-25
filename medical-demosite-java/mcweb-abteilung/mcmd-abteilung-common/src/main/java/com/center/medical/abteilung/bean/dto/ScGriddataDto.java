package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class ScGriddataDto implements Serializable {

    private static final long serialVersionUID = 6458703691659706423L;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;
}
