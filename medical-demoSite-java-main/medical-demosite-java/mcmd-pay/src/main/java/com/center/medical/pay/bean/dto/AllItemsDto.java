package com.center.medical.pay.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 获取支付项目数据
 */
@Data
public class AllItemsDto implements Serializable {
    private static final long serialVersionUID = -8513056622772950169L;

    @ApiModelProperty(value = "id")
    private String id;


    @ApiModelProperty(value = "价格")
    private BigDecimal price;


}
