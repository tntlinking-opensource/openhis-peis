package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 客单价及折扣率
 */
@Data
public class CustomerPriceDto implements Serializable {
    private static final long serialVersionUID = 2039591181016120459L;

    @ApiModelProperty(value = "分中心名称")
    private String name;

    @ApiModelProperty(value = "上次客单价")
    private Double lastCustomerPrice;

    @ApiModelProperty(value = "这次客单价")
    private Double thisCustomerPrice;

    @ApiModelProperty(value = "上次折扣率")
    private Double lastDiscountRate;

    @ApiModelProperty(value = "这次折扣率")
    private Double thisDiscountRate;

    @ApiModelProperty(value = "上次年份")
    private Integer lastYear;

    @ApiModelProperty(value = "这次年份")
    private Integer thisYear;


    public CustomerPriceDto(String name, Double lastCustomerPrice, Double thisCustomerPrice, Double lastDiscountRate, Double thisDiscountRate, Integer lastYear, Integer thisYear) {
        this.name = name;
        this.lastCustomerPrice = lastCustomerPrice;
        this.thisCustomerPrice = thisCustomerPrice;
        this.lastDiscountRate = lastDiscountRate;
        this.thisDiscountRate = thisDiscountRate;
        this.lastYear = lastYear;
        this.thisYear = thisYear;
    }

    public CustomerPriceDto() {
    }
}
