package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  家庭卡消费参数
 */
@Data
public class SaveConParam implements Serializable {
    private static final long serialVersionUID = -5659887011785387033L;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "卡号")
    private String cardNo;

    @ApiModelProperty(value = "操作类型：0.充值 1.消费 2.退费")
    private String type;

    @ApiModelProperty(value = "金额")
    private Double money;

    @ApiModelProperty(value = "积分")
    private Double jf;

    @ApiModelProperty(value = "操作人姓名")
    private String chargeUsername;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "消费类型，详见：CardConsumeType")
    private String consumetype;

}
