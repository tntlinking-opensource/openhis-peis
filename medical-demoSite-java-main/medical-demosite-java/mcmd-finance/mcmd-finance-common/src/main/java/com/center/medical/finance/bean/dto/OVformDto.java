package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 存放总数据或增长率
 */
@Data
public class OVformDto implements Serializable {
    private static final long serialVersionUID = -3612808030399327333L;

    @ApiModelProperty(value = "体检产值(万元)")
    private Double value;

    @ApiModelProperty(value = "同时增长率")
    private String growthRate;


    public OVformDto() {
    }

    public OVformDto(Double value, String growthRate) {
        this.value = value;
        this.growthRate = growthRate;
    }
}
