package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 团检专属卡-套餐搜索返回数据
 */
@Data
public class OrderMealVo implements Serializable {
    private static final long serialVersionUID = -8494685870756244653L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;
}
