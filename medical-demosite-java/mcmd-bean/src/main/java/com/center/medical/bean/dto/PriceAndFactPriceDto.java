package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取原价和优惠价
 */
@Data
public class PriceAndFactPriceDto implements Serializable {
    private static final long serialVersionUID = -5951667689832310457L;

    @ApiModelProperty(value = "原始价格合计")
    private Double price;

    @ApiModelProperty(value = "优惠价格合计")
    private Double factprice;
}
