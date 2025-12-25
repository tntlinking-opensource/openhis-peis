package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 当日线下业绩 选项数据
 */
@Data
public class DSVOptionDto implements Serializable {
    private static final long serialVersionUID = -6553677381089320917L;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "总金额")
    private Double amount;

    @ApiModelProperty(value = "名称")
    private String typeName;


    public DSVOptionDto(String fzx, Double amount, String typeName) {
        this.fzx = fzx;
        this.amount = amount;
        this.typeName = typeName;
    }

    public DSVOptionDto() {
    }
}
