package com.center.medical.sellcrm.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登记列表-批量设置获取订单号下拉 返回数据
 */
@Data
public class DdhDataVo implements Serializable {
    private static final long serialVersionUID = 3162766221047573583L;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "客户名称")
    private String customername;

    @ApiModelProperty(value = "客户id")
    private String customerId;
}
