package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 发票管理 分页 参数
 */
@Data
public class IRPageParam implements Serializable {
    private static final long serialVersionUID = 7856351419940213752L;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "体检类型")
    private String tjType;

    @ApiModelProperty(value = "发票抬头")
    private String fptt;

    @ApiModelProperty(value = "搜索销售经理")
    private String ssxsjl;

    @ApiModelProperty(value = "搜索销售经理 标识")
    private String flag;

    @ApiModelProperty(value = "搜索订单号")
    private String ssddh;

    @ApiModelProperty(value = "发票面额，精准查询")
    private String receiptcorenumber;
}
