package com.center.medical.enterprise.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单详情 初始化返回数据
 */
@Data
public class CreateOrderInfoDataVo implements Serializable {
    private static final long serialVersionUID = 7408764150774837582L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "计划检期从")
    private String jhjqc;

    @ApiModelProperty(value = "计划检期到")
    private String jhjqd;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "创建订单日期")
    private String cjddrq;

    @ApiModelProperty(value = "intId")
    private String tjm;
}
