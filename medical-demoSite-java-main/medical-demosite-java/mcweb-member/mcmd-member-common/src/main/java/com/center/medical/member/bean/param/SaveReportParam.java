package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 中心会员-会员卡挂失
 */
@Data
public class SaveReportParam implements Serializable {
    private static final long serialVersionUID = -493382508199850753L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String idSex;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "会员级别")
    private String memberlevel;

    @ApiModelProperty(value = "剩余积分")
    private String balanceJf;

    @ApiModelProperty(value = "补办卡号")
    private String patientcardno;

    @ApiModelProperty(value = "积分")
    private String limit;

    @ApiModelProperty(value = "备注")
    private String note;
}
