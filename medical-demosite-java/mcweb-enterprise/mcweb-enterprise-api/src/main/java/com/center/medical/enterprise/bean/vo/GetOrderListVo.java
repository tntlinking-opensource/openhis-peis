package com.center.medical.enterprise.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取订单列表
 */
@Data
public class GetOrderListVo implements Serializable {
    private static final long serialVersionUID = 3539304009713406426L;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "团检码")
    private String tjm;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "分中心名字")
    private String fzx;

    @ApiModelProperty(value = "计划检期从")
    private String jhjqc;

    @ApiModelProperty(value = "计划检期到")
    private String jhjqd;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "创建订单日期")
    private String cjddrq;
}
