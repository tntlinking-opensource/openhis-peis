package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 *  收费项目下拉参数
 */
@Data
public class ListDataParam implements Serializable {
    private static final long serialVersionUID = 8699722229044735869L;

    @ApiModelProperty(value = "订单号,不传这个返回价格，传这个(并且符合条件)返回外部价")
    private String numorgresv;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "priceKey")
    private String pricekey;

    @ApiModelProperty(value = "输入码")
    private String key;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "分中心id")
    private String cid;

}
