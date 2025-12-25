package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FindJcrsDto implements Serializable {
    private static final long serialVersionUID = -520992122223737691L;

    @ApiModelProperty(value = "jbmc")
    private String jbmc;

    @ApiModelProperty(value = "count")
    private int count;
}
