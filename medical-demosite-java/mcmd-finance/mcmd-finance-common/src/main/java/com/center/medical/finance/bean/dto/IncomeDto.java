package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检收入
 */
@Data
public class IncomeDto implements Serializable {
    private static final long serialVersionUID = -1077381587126660396L;

    @ApiModelProperty(value = "分中心名称")
    private String name;

    @ApiModelProperty(value = "上次收入")
    private Double lastIncome;

    @ApiModelProperty(value = "这次收入")
    private Double thisIncome;

    @ApiModelProperty(value = "上次人数")
    private Double lastCount;

    @ApiModelProperty(value = "这次人数")
    private Double thisCount;

    @ApiModelProperty(value = "上次年份")
    private Integer lastYear;

    @ApiModelProperty(value = "这次年份")
    private Integer thisYear;

    public IncomeDto(String name, Double lastIncome, Double thisIncome, Double lastCount, Double thisCount, Integer lastYear, Integer thisYear) {
        this.name = name;
        this.lastIncome = lastIncome;
        this.thisIncome = thisIncome;
        this.lastCount = lastCount;
        this.thisCount = thisCount;
        this.lastYear = lastYear;
        this.thisYear = thisYear;
    }

    public IncomeDto() {
    }
}
