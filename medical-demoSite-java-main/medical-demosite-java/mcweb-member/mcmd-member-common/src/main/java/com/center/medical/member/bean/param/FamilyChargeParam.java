package com.center.medical.member.bean.param;

import com.center.medical.bean.param.BaseParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 家庭卡消费记录分页参数
 */
@Data
public class FamilyChargeParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = -2964493596570124L;

    @ApiModelProperty(value = "卡号")
    private String cardno;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "消费类型，详见：CardConsumeType")
    private String consumetype;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "充值前金额")
    private Double startMoney;

    @ApiModelProperty(value = "充值后金额")
    private Double endMoney;



}
