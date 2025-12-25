package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 变动成本率参数
 */
@Data
public class VariableCostRateDto implements Serializable {
    private static final long serialVersionUID = 957030332446544068L;


    @ApiModelProperty(value = "成本价")
    private Double costprice;

    @ApiModelProperty(value = "折后价格")
    private Double zhjg;

    @ApiModelProperty(value = "人员数量")
    private Double num;

    @ApiModelProperty(value = "套餐id")
    private String tcid;
}
