package com.center.medical.member.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 分页查询平台会员列表返回数据
 */
@Data
public class MemberListVo implements Serializable {
    private static final long serialVersionUID = -5216318026114868232L;

    @Excel(name = "id")
    @ApiModelProperty(value = "ID")
    private String id;

    @Excel(name = "会员卡号")
    @ApiModelProperty(value = "会员卡号")
    private String patientcardno;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @Excel(name = "可用积分")
    @ApiModelProperty(value = "会员卡剩余积分（会员卡和家庭卡使用）")
    private Double balanceJf;

    @Excel(name = "性别", readConverterExp = "0=男,1=女")
    @ApiModelProperty(value = "性别ID")
    private String idSex;

    @Excel(name = "电话")
    @ApiModelProperty(value = "手机号")
    private String phone;

    @Excel(name = "会员级别", readConverterExp = "1=普通会员,2=VIP会员,3=VVIP会员")
    @ApiModelProperty(value = "会员级别，")
    private String memberlevel;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "创建人")
    @ApiModelProperty(value = "创建人")
    private String membercreate;

    @ApiModelProperty(value = "使用套餐完成登记的时间")
    private Date tcDateregister;

    @ApiModelProperty(value = "使用赠送套餐完成登记的体检号")
    private String tcPatientcode;

    @ApiModelProperty(value = "赠送套餐是否已用：0或null.否 1.是")
    private Integer tcChecked;

    @ApiModelProperty(value = "复查额度")
    private String recheckMoney;

    @ApiModelProperty(value = "套餐id")
    private String tcId;

    @Excel(name = "客户经理")
    @ApiModelProperty(value = "客户经理")
    private String khjl;

    @Excel(name = "生日")
    @ApiModelProperty(value = "出生日期")
    private Date birthdate;

    @Excel(name = "身份证号")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @Excel(name = "单位")
    @ApiModelProperty(value = "单位")
    private String dw;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;

    @Excel(name = "创建日期")
    @ApiModelProperty(value = "提交时间")
    private Date createdate;

    @ApiModelProperty(value = "卡类型ID")
    private String typeId;

    @ApiModelProperty(value = "套餐名称")
    private String tcmc;

    @ApiModelProperty(value = "卡号集合")
    private List<String> cardnos;

}
