package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取活动套餐种类
 */
@Data
public class ActivityMealVo implements Serializable {
    private static final long serialVersionUID = 2756758544617805318L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "活动套餐名称")
    private String tjtcmc;
}
