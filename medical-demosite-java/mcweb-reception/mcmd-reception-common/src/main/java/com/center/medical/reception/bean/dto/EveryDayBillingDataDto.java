package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 每日账单数据
 */
@Data
public class EveryDayBillingDataDto implements Serializable {
    private static final long serialVersionUID = 8850730546934524806L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "付款方式名称")
    private String paywayName;

    @ApiModelProperty(value = "结算日期")
    private String moneyamountpaiddate;
}
