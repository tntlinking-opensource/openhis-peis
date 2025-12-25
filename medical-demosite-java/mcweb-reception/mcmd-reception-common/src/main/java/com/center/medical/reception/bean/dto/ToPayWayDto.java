package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 当日所有检查实收的费用统计返回数据
 */
@Data
public class ToPayWayDto implements Serializable {

    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @ApiModelProperty(value = "团检")
    private Double org;

    @ApiModelProperty(value = "个检")
    private Double pers;

    @ApiModelProperty(value = "团检实付金额")
    private Double orgjs;

    @ApiModelProperty(value = "个检实付金额")
    private Double perjs;

    @ApiModelProperty(value = "疫苗")
    private Double yimiao;

    @ApiModelProperty(value = "个检")
    private Double per;

    @ApiModelProperty(value = "total")
    private Double total;

}
