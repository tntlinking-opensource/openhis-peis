package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 发票管理 保存参数
 */
@Data
public class IRSaOrUpParam implements Serializable {
    private static final long serialVersionUID = 5518088679970463428L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "发票类型ID")
    private String idReceipttype;

    @ApiModelProperty(value = "发票抬头")
    private String fptt;

    @ApiModelProperty(value = "发票面额")
    private Double receiptcorenumber;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "体检团体")
    private String ttjt;

    @ApiModelProperty(value = "发票号码")
    private String receiptsheetno;

    @ApiModelProperty(value = "发表申请时间")
    private Date applicationTime;

    @ApiModelProperty(value = "换票原因")
    private String ttyy;

    @ApiModelProperty(value = "原发票号")
    private String firstReceiptsheetno;
}
