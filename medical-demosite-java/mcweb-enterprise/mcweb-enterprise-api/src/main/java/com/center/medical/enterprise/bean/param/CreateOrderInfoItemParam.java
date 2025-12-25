package com.center.medical.enterprise.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 套餐详情 分页条件
 */
@Data
public class CreateOrderInfoItemParam implements Serializable {
    private static final long serialVersionUID = 2718739546092009073L;

    @ApiModelProperty(value = "套餐id")
    private String tcid;

    @ApiModelProperty(value = "普通套餐标示：0.普通套餐")
    private String combostate;

}
