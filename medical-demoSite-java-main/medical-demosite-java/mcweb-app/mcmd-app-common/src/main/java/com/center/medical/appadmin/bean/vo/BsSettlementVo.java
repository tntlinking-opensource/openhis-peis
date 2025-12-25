package com.center.medical.appadmin.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务结算表返回数据
 */
@Data
public class BsSettlementVo implements Serializable {
    private static final long serialVersionUID = 2196770544918045397L;

    @ApiModelProperty(value = "支付结算单据ID")
    private String settlementId;

    @ApiModelProperty(value = "外部订单流水号")
    private String bizPayNo;

    @ApiModelProperty(value = "关联id")
    private String orderId;

    @ApiModelProperty(value = "第三方系统的订单号")
    private String bizOrderNo;

    @ApiModelProperty(value = "支付方式 1:微信支付 2:支付宝")
    private Integer payType;

    @ApiModelProperty(value = "支付方式名称")
    private String payTypeName;

    @ApiModelProperty(value = "支付积分")
    private Double payScore;

    @ApiModelProperty(value = "支付金额")
    private Double payAmount;

    @ApiModelProperty(value = "是否退款 0:否 1:是")
    private Integer isClearing;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "手机号")
    private String userMobile;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "退款时间")
    private Date clearingTime;

    @ApiModelProperty(value = "回调时间")
    private Date callbackTime;

    @ApiModelProperty(value = "支付状态 0:未支付 1:已支付")
    private Integer payStatus;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;

    @ApiModelProperty(value = "分中心")
    private String fzx;
}
