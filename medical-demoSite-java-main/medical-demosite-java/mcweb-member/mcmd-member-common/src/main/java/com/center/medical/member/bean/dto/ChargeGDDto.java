package com.center.medical.member.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增家庭会员办理-chargeGriddata收费信息参数
 */
@Data
public class ChargeGDDto implements Serializable {
    private static final long serialVersionUID = 3079897531253411397L;

    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @ApiModelProperty(value = "结算日期")
    private Date moneyamountpaiddate;

    @ApiModelProperty(value = "实付")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "收费员")
    private String feecharger;

    @ApiModelProperty(value = "支付方式")
    private String idPayWay;

    @ApiModelProperty(value = "状态 removed删除，modified修改，added添加")
    private String state;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "是否统收：0:不是统收1：统收")
    private Integer isTotalcharge;



}
