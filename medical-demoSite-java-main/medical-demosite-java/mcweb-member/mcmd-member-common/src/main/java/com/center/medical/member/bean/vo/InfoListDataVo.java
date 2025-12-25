package com.center.medical.member.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取其他家庭成员信息 返回信息
 */
@Data
public class InfoListDataVo implements Serializable {
    private static final long serialVersionUID = -489962211118753413L;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @ApiModelProperty(value = "一卡通号")
    private String patientcardno;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "拼音输入码")
    private String inputCode;

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

    @ApiModelProperty(value = "分中心名称")
    private String fzxName;

    @ApiModelProperty(value = "会员级别名称")
    private String levelName;

    @ApiModelProperty(value = "名称")
    private String typeName;

    @ApiModelProperty(value = "是否主持卡人  1是  0或null不是")
    private Integer isMain;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "vip、vvip等等")
    private String memberlevel;

    @ApiModelProperty(value = "客户经理")
    private String khjl;

    @ApiModelProperty(value = "客户经理名称")
    private String khjlName;

    @ApiModelProperty(value = "档案来源  1家庭卡、超级会员管理新增的档案")
    private Integer source;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;
}
