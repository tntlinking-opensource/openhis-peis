package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 活动套餐-列表返回数据
 */
@Data
public class ActivityMealListVo implements Serializable {
    private static final long serialVersionUID = 3870024108618627467L;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "活动套餐名称")
    private String tjtcmc;

    @ApiModelProperty(value = "金额")
    private String amount;

    @ApiModelProperty(value = "金额")
    private String amountTotal;


    public ActivityMealListVo(String fzx, String id, String tjtcmc, String amount, String amountTotal) {
        this.fzx = fzx;
        this.id = id;
        this.tjtcmc = tjtcmc;
        this.amount = amount;
        this.amountTotal = amountTotal;
    }

    public ActivityMealListVo() {
    }
}
