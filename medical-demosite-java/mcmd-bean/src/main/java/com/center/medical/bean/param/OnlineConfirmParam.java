package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  平安好医生 线上到检确认参数
 */
@Data
public class OnlineConfirmParam implements Serializable {
    private static final long serialVersionUID = 5658533299968779388L;

    @ApiModelProperty("健康互联网分配的唯一 key，用作校验用户是否合法")
    private String pajkKey;

    @ApiModelProperty("签名算法，用作校验用户是否合法MD5(pajkKey+pajkPwd+timestamp)")
    private String sign;

    @ApiModelProperty("时间戳，用作校验用户是否合法，")
    private String timestamp;

    @ApiModelProperty("医疗机构订单 ID")
    private String hospitalOrderId;

    @ApiModelProperty("体检平台订单 ID")
    private String orderId;

    @ApiModelProperty("订单状态：客户已到检：03")
    private String orderState;

    @ApiModelProperty("验证码")
    private String captcha;



}
