package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 新客户金额及数量
 */
@Data
public class NewCustomersDto implements Serializable {
    private static final long serialVersionUID = -5976103452810483953L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "金额")
    private Double money;

    @ApiModelProperty(value = "人数")
    private Double count;

    public NewCustomersDto(String name, Double money, Double count) {
        this.name = name;
        this.money = money;
        this.count = count;
    }

    public NewCustomersDto() {
    }
}
