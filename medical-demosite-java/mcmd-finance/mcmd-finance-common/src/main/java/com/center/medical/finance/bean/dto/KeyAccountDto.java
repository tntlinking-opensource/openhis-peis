package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检机构 大客户
 */
@Data
public class KeyAccountDto implements Serializable {
    private static final long serialVersionUID = -7667897109324889943L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "50万")
    private Double fiveHundred;

    @ApiModelProperty(value = "20万-50万")
    private Double TwoHundred;

    public KeyAccountDto(String name, Double fiveHundred, Double twoHundred) {
        this.name = name;
        this.fiveHundred = fiveHundred;
        TwoHundred = twoHundred;
    }

    public KeyAccountDto() {
    }
}
