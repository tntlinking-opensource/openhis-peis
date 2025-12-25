package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class JzjsSqlDto implements Serializable {
    private static final long serialVersionUID = -2165520283192224612L;


    @ApiModelProperty(value = "gr")
    private Double gr;


    @ApiModelProperty(value = "tt")
    private Double tt;
}
