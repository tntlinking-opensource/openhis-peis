package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 随行支付参数
 */
@Data
public class SuiXingPayParam implements Serializable {
    private static final long serialVersionUID = 3172510850534584889L;

    @ApiModelProperty(value = "商户订单号")
    private String ordNo;

    @ApiModelProperty(value = "订单总金额(元)，格式：#########.##")
    private String amt;

    @ApiModelProperty(value = "微信用户openId")
    private String userId;

    @ApiModelProperty(value = "回调地址")
    private String notifyUrl;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;

    public SuiXingPayParam(String ordNo, String amt, String userId, String notifyUrl, String fzxId) {
        this.ordNo = ordNo;
        this.amt = amt;
        this.userId = userId;
        this.notifyUrl = notifyUrl;
        this.fzxId = fzxId;
    }

    public SuiXingPayParam() {
    }
}
