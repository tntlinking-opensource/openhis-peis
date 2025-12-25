package com.center.medical.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2022-11-24 10:56
 * @description: 会员列表数据
 */
@Data
public class MemberVo implements Serializable {
    private static final long serialVersionUID = 5817193207093636408L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "档案号")
    private String patientarchiveno;

    @ApiModelProperty(value = "一卡通号")
    private String patientcardno;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "性别ID")
    private Integer idSex;

    @ApiModelProperty(value = "vip、vvip等等")
    private String memberlevel;

    @ApiModelProperty(value = "创建人")
    private String membercreate;

    @ApiModelProperty(value = "客户经理")
    private String khjl;

    @ApiModelProperty(value = "可用积分")
    private Double jf;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "单位")
    private String dw;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "客户经理名称")
    private String khjlmc;

    @ApiModelProperty(value = "会员卡剩余积分（会员卡和家庭卡使用）")
    private Double balanceJf;

    @ApiModelProperty(value = "出生日期")
    private Date birthdate;
}
