package com.center.medical.datascreen.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xhp
 * @since 2023-06-07 10:03
 */
@Data
public class BranchDataTotalNumberVo {
    @ApiModelProperty(value = "总人数")
    private int number;
    @ApiModelProperty(value = "总费用")
    private double amount;
    @ApiModelProperty(value = "折扣率")
    private String discountRate;
    @ApiModelProperty(value = "客单价")
    private String customerUnitPrice;
}
