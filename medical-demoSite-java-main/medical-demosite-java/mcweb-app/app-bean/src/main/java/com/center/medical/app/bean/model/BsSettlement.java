package com.center.medical.app.bean.model;


import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 业务结算表(BsSettlement)表实体类
 *
 * @author ay
 * @since 2024-06-17 16:01:58
 */
@Data
@TableName("md_bs_settlement")
@ApiModel(value = "BsSettlement", description = "业务结算表实体类")
public class BsSettlement extends Model<BsSettlement> implements Serializable {
    private static final long serialVersionUID = -12310953307847530L;

    @TableId(value = "settlement_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "支付结算单据ID")
    private String settlementId;


    @ApiModelProperty(value = "支付单号")
    private String payNo;


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

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    @ApiModelProperty(value = "退款时间")
    private Date clearingTime;


    @ApiModelProperty(value = "回调时间")
    private Date callbackTime;


    @ApiModelProperty(value = "版本号")
    private Integer version;


    @ApiModelProperty(value = "支付状态 0:未支付 1:已支付")
    private Integer payStatus;


    @ApiModelProperty(value = "支付金额版本号")
    private Integer changeAmountVersion;

    @ApiModelProperty(value = "订单来源 1:创建套餐")
    private Integer orderOrigin;

    @ApiModelProperty(value = "分中心id")
    private String fzxId;
}
