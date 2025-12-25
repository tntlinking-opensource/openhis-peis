package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 银行汇款结算 查询汇总金额
 */
@Data
public class BankAmountVo implements Serializable {
    private static final long serialVersionUID = -5726116798905115687L;

    @ApiModelProperty(value = "汇总金额")
    private String amount;

}
