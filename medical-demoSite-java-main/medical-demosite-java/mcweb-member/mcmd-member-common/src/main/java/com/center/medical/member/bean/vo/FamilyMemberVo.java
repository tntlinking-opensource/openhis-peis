package com.center.medical.member.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 家庭卡消费分页返回数据
 */
@Data
public class FamilyMemberVo implements Serializable {
    private static final long serialVersionUID = 4477774291644560509L;

    @ApiModelProperty(value = "一卡通号")
    private String patientcardno;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "会员卡剩余积分（会员卡和家庭卡使用）")
    private Double balanceJf;

    @ApiModelProperty(value = "剩余金额(只有家庭卡使用这个字段)")
    private Double balanceMoney;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "生日")
    private Date birthdate;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "单位")
    private String dw;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "会员等级")
    private String patientclass;

    @ApiModelProperty(value = "种类名称")
    private String typeName;

    @ApiModelProperty(value = "是否主持卡人  1是  0或null不是")
    private Integer isMain;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @ApiModelProperty(value = "ID")
    private String id;
}
