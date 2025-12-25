package com.center.medical.pay.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderInfoByPatientCodeDto implements Serializable {
    private static final long serialVersionUID = 2140491251643135278L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "订单号")
    private String tradeNo;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "主表ID")
    private String chargemainid;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

}
