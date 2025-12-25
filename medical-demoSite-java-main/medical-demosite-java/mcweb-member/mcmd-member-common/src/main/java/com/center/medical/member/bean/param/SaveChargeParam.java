package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 家庭会员充值-保存参数
 */
@Data
public class SaveChargeParam implements Serializable {

    private static final long serialVersionUID = -8732616304314499143L;

    @ApiModelProperty(value = "一卡通号")
    private String patientcardno;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "积分")
    private Double jf;

    @ApiModelProperty(value = "充值前积分")
    private Double startJf;

    @ApiModelProperty(value = "金额")
    private Double money;

    @ApiModelProperty(value = "充值前金额")
    private Double startMoney;

    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "操作人姓名")
    private String chargeUsername;
}
