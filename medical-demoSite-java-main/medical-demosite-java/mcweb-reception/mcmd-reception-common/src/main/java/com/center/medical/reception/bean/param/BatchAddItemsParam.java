package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 批量加项参数
 */
@Data
public class BatchAddItemsParam implements Serializable {
    private static final long serialVersionUID = -3333869309519989313L;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "项目id")
    private String itemsId;

    @ApiModelProperty(value = "付款方式id")
    private String idPayway;

    @ApiModelProperty(value = "优惠价")
    private Double price;
}
