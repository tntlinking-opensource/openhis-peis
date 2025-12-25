package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 积分充值FormData参数
 */
@Data
public class JFFormDataParam implements Serializable {
    private static final long serialVersionUID = -4856370534470302841L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "分中心ID")
    private String branchCenter;

    @ApiModelProperty(value = "一卡通号")
    private String patientcardno;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "会员卡剩余积分（会员卡和家庭卡使用）")
    private Double balanceJf;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "积分")
    private String limit;

    @ApiModelProperty(value = "备注")
    private String memotext;
}
