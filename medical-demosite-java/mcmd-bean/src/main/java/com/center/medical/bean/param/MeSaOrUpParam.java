package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 中心会员添加
 */
@Data
public class MeSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -3348116176555578544L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private Integer idSex;

    @ApiModelProperty(value = "单位")
    private String dw;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "生日")
    private Date birthdate;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "会员卡号")
    private String patientcardno;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "会员级别，")
    private String memberlevel;

    @ApiModelProperty(value = "客户经理")
    private String khjl;

    @ApiModelProperty(value = "套餐id")
    private String tcId;

    @ApiModelProperty(value = "复查额度")
    private String recheckMoney;

    @ApiModelProperty(value = "体检卡")
    private String cards;

    @ApiModelProperty(value = "备注")
    private String note;
}
