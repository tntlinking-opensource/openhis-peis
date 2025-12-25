package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 金额数据
 */
@Data
public class TJKDataParam implements Serializable {
    private static final long serialVersionUID = -111240036164753597L;

    @ApiModelProperty(value = "金额应付")
    private Double moneyamount;

    @ApiModelProperty(value = "金额实付")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "是否统收：0:不是统收 1：统收")
    private Integer isTotalcharge;

    @ApiModelProperty(value = "已收费：0.未收费 1.已收费")
    private Integer fFeecharged;

    @ApiModelProperty(value = "操作标识：{removed:移除，modified:修改，added：新增}")
    private String _state;

    @ApiModelProperty(value = "已收")
    private Double yishou;

    @ApiModelProperty(value = "已退")
    private Double yitui;

    @ApiModelProperty(value = "付款方式id")
    private String idPayway;

    @ApiModelProperty(value = "是否可以编辑卡号：0.可以 1.不可以")
    private Integer isChange;

    @ApiModelProperty(value = "卡号")
    private String cardno;

    @ApiModelProperty(value = "是否卡号收款：0.否 1.是")
    private Integer kashou;

    @ApiModelProperty(value = "扣款方式：1.体检卡 2.会员卡积分 3.微信扫码枪 4.支付宝扫码枪 5.会员卡余额 6.家庭卡余额 7.家庭卡积分 8.复查额度")
    private Integer kkfs;

    @ApiModelProperty(value = "支付平台交易流水号")
    private String txTradeNo;

    @ApiModelProperty(value = "是否加项,0=否,1=是")
    private String isAdd;

}
