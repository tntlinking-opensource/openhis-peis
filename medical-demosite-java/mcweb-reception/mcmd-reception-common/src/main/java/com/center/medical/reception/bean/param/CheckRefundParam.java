package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 判断支付方式的金额是否可以退
 */
@Data
public class CheckRefundParam implements Serializable {
    private static final long serialVersionUID = -7581567909343947515L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "金额实付")
    private Double moneyamountpaid;

}
