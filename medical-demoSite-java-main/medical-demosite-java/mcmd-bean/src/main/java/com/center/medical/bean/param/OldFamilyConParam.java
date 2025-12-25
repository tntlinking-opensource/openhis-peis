package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 家庭卡消费 数据
 */
@Data
public class OldFamilyConParam implements Serializable {
    private static final long serialVersionUID = 1317695279135667456L;

    @ApiModelProperty(value = "授权码（不用添）")
    private String authCode;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "家庭卡号")
    private String cardNo;

    @ApiModelProperty(value = "收费⽅式id 18:家庭卡积分，19:家庭卡余额")
    private String idPayway;

    @ApiModelProperty(value = "操作类型 1消费 0充值 （不用添）")
    private String type;

    @ApiModelProperty(value = "⾦额(消费和充值都是正数)")
    private Double money;

    @ApiModelProperty(value = "操作⼈⽤户名")
    private String chargeUsername;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "体检号")
    private String patientcode;


    public OldFamilyConParam() {
    }

    public OldFamilyConParam(String authCode, String idcardno, String cardNo, String idPayway, String type, Double money, String chargeUsername, String note, String patientcode) {
        this.authCode = authCode;
        this.idcardno = idcardno;
        this.cardNo = cardNo;
        this.idPayway = idPayway;
        this.type = type;
        this.money = money;
        this.chargeUsername = chargeUsername;
        this.note = note;
        this.patientcode = patientcode;
    }
}
