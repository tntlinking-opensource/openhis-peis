package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 会员卡退款FormData参数
 */
@Data
public class RMFormDataParam implements Serializable {
    private static final long serialVersionUID = 3936816994149218032L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "分中心ID")
    private String branchCenter;

    @ApiModelProperty(value = "会员卡号")
    private String patientcardno;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "会员卡剩余积分（会员卡和家庭卡使用）")
    private Double balanceJf;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "操作人用户名")
    private String userName;

    @ApiModelProperty(value = "记录增加或减少的金额或者积分：大于0增加、小于0减少")
    private Double limit;

    @ApiModelProperty(value = "备注")
    private String memotext;

    @ApiModelProperty(value = "消费类型ID", position = 9, required = true)
    private String consumeType;
}
