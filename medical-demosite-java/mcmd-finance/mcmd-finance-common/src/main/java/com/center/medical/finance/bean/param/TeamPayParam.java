package com.center.medical.finance.bean.param;

import com.center.medical.bean.enums.Kkfs;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2023/5/26 11:41
 * @description: 团体支付参数（会员卡、体检卡等）
 */
@Data
public class TeamPayParam implements Serializable {
    private static final long serialVersionUID = -8703866414116116274L;

    @ApiModelProperty(value = "团体结算ID", position = 1, required = true)
    private String id;

    @ApiModelProperty(value = "订单编号", position = 2, required = true)
    private String orderId;

    @ApiModelProperty(value = "应收金额 (弃用：应该是动态计算所有统收已收的金额)")
    private Double shouldMoney;

    @ApiModelProperty(value = "实收金额(结算金额)")
    private Double realityMoney;

    @ApiModelProperty(value = "团体ID")
    private String groupId;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "卡号(体检卡、会员卡等)/付款码(微信、支付宝付款码)", position = 4, required = true)
    private String cardId;

    @ApiModelProperty(value = "备注", position = 11)
    private String memotext;

    @ApiModelProperty(value = "支付方式ID", position = 3, required = true)
    private String idPayway;

    @ApiModelProperty(value = "分中心ID", position = 8, required = true)
    private String branchId;

    /**
     * @see Kkfs
     */
    @ApiModelProperty(value = "扣款方式：1.体检卡 2.会员卡积分 3.微信扫码枪 4.支付宝扫码枪 5.会员卡余额 6.家庭卡余额 7.家庭卡积分", position = 9, required = true)
    private Integer kkfs;
}
