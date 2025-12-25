package com.center.medical.member.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 会员管理-沟通记录分页返回数据
 */
@Data
public class InterflowVo implements Serializable {
    private static final long serialVersionUID = 4237541670637073023L;

    @Excel(name = "id")
    @ApiModelProperty(value = "ID")
    private String id;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "会员姓名")
    private String patientname;

    @Excel(name = "性别" ,readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别")
    private String idSex;

    @Excel(name = "电话")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Excel(name = "身份证号")
    @ApiModelProperty(value = "会员身份证")
    private String idCard;

    @Excel(name = "会员级别" ,readConverterExp = "0=VIP会员,1=普通会员,2=VVIP会员")
    @ApiModelProperty(value = "会员级别")
    private String memberLeaver;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "客户经理")
    @ApiModelProperty(value = "客户经理")
    private String khjl;

    @Excel(name = "单位")
    @ApiModelProperty(value = "单位")
    private String dw;

    @Excel(name = "跟进内容")
    @ApiModelProperty(value = "跟进内容")
    private String text;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String memo;

    @Excel(name = "跟进人")
    @ApiModelProperty(value = "跟进人")
    private String visitPerson;

    @Excel(name = "跟进时间")
    @ApiModelProperty(value = "跟进时间")
    private String visitTime;

    @Excel(name = "跟进结果")
    @ApiModelProperty(value = "跟进结果")
    private String visitResult;

}
