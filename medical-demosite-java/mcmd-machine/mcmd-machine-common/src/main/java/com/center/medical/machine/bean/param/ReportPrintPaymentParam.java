package com.center.medical.machine.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ReportPrintPaymentParam implements Serializable {

    @NotBlank(message = "请填写体检号!")
    @ApiModelProperty(value = "体检号")
    private String patientcode;


    @NotNull(message = "请填写交易方式!")
    @ApiModelProperty(value = "交易方式，生成二维码时需要这个参数，W01 微信扫码支付，A01 支付宝扫码支付")
    private String paytype;
}
