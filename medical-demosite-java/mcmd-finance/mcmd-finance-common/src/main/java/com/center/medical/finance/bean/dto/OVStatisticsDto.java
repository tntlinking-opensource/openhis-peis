package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 产值报表 统计数据
 */
@Data
public class OVStatisticsDto implements Serializable {
    private static final long serialVersionUID = 8971536005251071766L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "产值（万元）")
    private Integer outputValue;

    @ApiModelProperty(value = "增长率")
    private String growthRate;

    public OVStatisticsDto(String name, Integer outputValue, String growthRate) {
        this.name = name;
        this.outputValue = outputValue;
        this.growthRate = growthRate;
    }

    public OVStatisticsDto() {
    }
}
