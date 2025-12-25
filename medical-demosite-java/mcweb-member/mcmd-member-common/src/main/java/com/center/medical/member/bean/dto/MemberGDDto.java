package com.center.medical.member.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增家庭会员办理-memberGriddata 其他家庭成员信息参数
 */
@Data
public class MemberGDDto implements Serializable {
    private static final long serialVersionUID = -4778043509429217748L;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "剩余金额(只有家庭卡使用这个字段)")
    private Double balanceMoney;

    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @ApiModelProperty(value = "一卡通号")
    private String patientcardno;

    @ApiModelProperty(value = "分中心名称")
    private String fzxName;

    @ApiModelProperty(value = "分中心名字")
    private String fzx;

    @ApiModelProperty(value = "会员级别")
    private String memberlevel;

    @ApiModelProperty(value = "是否主持卡人  1是  0或null不是")
    private Integer isMain;

    @ApiModelProperty(value = "种类名称")
    private String typeName;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "会员级别名称")
    private String levelName;

    @ApiModelProperty(value = "客户经理")
    private String khjl;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "会员卡剩余积分（会员卡和家庭卡使用）")
    private Double balanceJf;

    @ApiModelProperty(value = "单位")
    private String dw;

    @ApiModelProperty(value = "客户经理名称")
    private String khjlName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "状态 removed删除，modified修改，added添加")
    private String state;
}
