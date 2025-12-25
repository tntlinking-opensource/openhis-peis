package com.center.medical.bean.param;

import com.center.medical.bean.enums.Kkfs;
import com.center.medical.bean.enums.CardConsumeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2023/4/12 17:06
 * @description: 卡消费信息
 */
@Data
@ApiModel(value = "卡消费信息")
public class CardConsumeParam implements Serializable {
    private static final long serialVersionUID = 6625977846086257989L;

    @ApiModelProperty(value = "卡ID")
    private String cardId;

    @ApiModelProperty(value = "记录增加或减少的金额或者积分：大于0增加、小于0减少")
    private Double limit;

    @ApiModelProperty(value = "卡操作：0.为充值 1.为消费 2.退费")
    private Integer isAdd;

    @ApiModelProperty(value = "备注")
    private String memotext;

    @ApiModelProperty(value = "操作人ID")
    private String operationId;

    @ApiModelProperty(value = "付款方式ID")
    private String payMode;

    @ApiModelProperty(value = "分中心ID")
    private String branchCenter;

    @ApiModelProperty(value = "体检号")
    private String chargeId;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    /**
     * @see CardConsumeType
     */
    @ApiModelProperty(value = "消费类型：0.体检 1.药房 2.口腔科 3.眼镜店 4.合作 5.保健品 6.团体结算")
    private String consumetype;

    /**
     * @see Kkfs
     */
    @ApiModelProperty(value = "扣款方式：1.体检卡 2.会员卡积分 3.微信扫码枪 4.支付宝扫码枪 5.会员卡余额 6.家庭卡余额 7.家庭卡积分 8.复查额度", position = 9, required = true)
    private Integer kkfs;

    @ApiModelProperty(value = "收费员名称", position = 10)
    private String userName;

    /**
     * 支付流水ID：卡扣款则是卡操作记录ID，微信或者支付宝在线支付则是第三方支付流水号
     */
    @ApiParam(hidden = true)
    private String consumeId;

    public CardConsumeParam(String cardId, Double limit, Integer isAdd, String memotext, String operationId, String payMode, String branchCenter, String chargeId, String patientname, String consumetype, Integer kkfs, String userName, String consumeId) {
        this.cardId = cardId;
        this.limit = limit;
        this.isAdd = isAdd;
        this.memotext = memotext;
        this.operationId = operationId;
        this.payMode = payMode;
        this.branchCenter = branchCenter;
        this.chargeId = chargeId;
        this.patientname = patientname;
        this.consumetype = consumetype;
        this.kkfs = kkfs;
        this.userName = userName;
        this.consumeId = consumeId;
    }


    public CardConsumeParam() {
    }
}
