package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 *  产值情况
 */
@Data
public class OutputValueDto implements Serializable {
    private static final long serialVersionUID = -1556832290023166272L;

    @ApiModelProperty(value = "指标")
    private String index;

    @ApiModelProperty(value = "年份")
    private Integer Year;

    @ApiModelProperty(value = "内容")
    private List<Double> value;



    public OutputValueDto() {
    }


    public OutputValueDto(String index, Integer year, List<Double> value) {
        this.index = index;
        Year = year;
        this.value = value;
    }


}
