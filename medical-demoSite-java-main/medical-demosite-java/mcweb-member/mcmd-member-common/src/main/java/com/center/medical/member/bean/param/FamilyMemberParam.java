package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * 家庭会员分页参数
 */
@Data
public class FamilyMemberParam implements Serializable {
    private static final long serialVersionUID = 8588073363407844289L;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "卡种类")
    private String card;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "卡号")
    private String cardno;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "开始月份")
    private String startMoney;

    @ApiModelProperty(value = "结束月份")
    private String endMoney;
}
