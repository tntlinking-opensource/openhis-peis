package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import lombok.Data;

import java.util.Date;

/**
 * 小程序微信订单(AppOrder)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:11:46
 */
@Data
@TableName("APP_ORDER")
@ApiModel(value = "AppOrder", description = "小程序微信订单实体类")
public class AppOrder extends Model<AppOrder> implements Serializable {
    private static final long serialVersionUID = 812765804648529751L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "${column.comment}")
    private String id;

    @ApiModelProperty(value = "${column.comment}")
    private Date createdate;

    @ApiModelProperty(value = "${column.comment}")
    private Date modifydate;

    @ApiModelProperty(value = "APPUSER.ID")
    private String userId;

    @ApiModelProperty(value = "1单位  2个人")
    private Integer isGroup;

    @ApiModelProperty(value = "商户订单号  单位体检为patientid  个人体检为随机生成的临时号")
    private String outTradeNo;

    @ApiModelProperty(value = "个人体检  套餐id createcombo")
    private String tcid;

    @ApiModelProperty(value = "接收结果通知  最后修改状态时间")
    private Date localTime;

    @ApiModelProperty(value = "交易状态")
    private String tradeState;

    @ApiModelProperty(value = "微信支付完成时间")
    private String successTime;

    @ApiModelProperty(value = "订单总金额")
    private String total;

    @ApiModelProperty(value = "用户支付金额")
    private String payerTotal;

    @ApiModelProperty(value = "币种")
    private String currency;

    @ApiModelProperty(value = "用户支付币种")
    private String payerCurrency;

    @ApiModelProperty(value = "终端设备号（门店号或收银设备ID）")
    private String deviceId;

    @ApiModelProperty(value = "付款银行")
    private String bankType;

    @ApiModelProperty(value = "交易状态描述")
    private String tradeStateDesc;

    @ApiModelProperty(value = "交易类型")
    private String tradeType;

    @ApiModelProperty(value = "微信支付订单号")
    private String transactionId;

    @ApiModelProperty(value = "${column.comment}")
    private String price;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isFamily;

    @ApiModelProperty(value = "${column.comment}")
    private String familyId;

    @ApiModelProperty(value = "${column.comment}")
    private String branchId;

    @ApiModelProperty(value = "${column.comment}")
    private String prepayId;

    @ApiModelProperty(value = "${column.comment}")
    private String patientId;

    @ApiModelProperty(value = "${column.comment}")
    private String orderType;

    @ApiModelProperty(value = "${column.comment}")
    private String appUserQuestionnaireId;

    @ApiModelProperty(value = "${column.comment}")
    private String consultId;

    @ApiModelProperty(value = "${column.comment}")
    private String laborCost;

    @ApiModelProperty(value = "${column.comment}")
    private String editId;
}
