package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 活动套餐追踪进度 分页参数
 */
@Data
public class GetActivityMealParam implements Serializable {
    private static final long serialVersionUID = 5986996768272129926L;

    @ApiModelProperty(value = "活动套餐名称")
    private String tjtcmc;
}
