package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 骨密度JsonData键值对
 */
@Data
public class BmdJsonDataDto implements Serializable {
    private static final long serialVersionUID = 8195772390765612065L;

    @ApiModelProperty(value = "键")
    private String key;

    @ApiModelProperty(value = "值")
    private String value;
}
