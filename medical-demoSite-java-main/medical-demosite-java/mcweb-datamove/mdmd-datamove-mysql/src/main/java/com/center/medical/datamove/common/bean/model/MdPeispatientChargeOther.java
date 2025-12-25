package com.center.medical.datamove.common.bean.model;


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
 * 体检者其他缴费(MdPeispatientChargeOther)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:11
 */
@Data
@TableName("md_peispatient_charge_other")
@ApiModel(value = "MdPeispatientChargeOther", description = "体检者其他缴费实体类")
public class MdPeispatientChargeOther extends Model<MdPeispatientChargeOther> implements Serializable {
    private static final long serialVersionUID = 850906150389493374L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "交易号")
    private String transactionNo;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "费用名称")
    private String feeName;

    @ApiModelProperty(value = "待支付金额")
    private Double price;

    @ApiModelProperty(value = "支付金额")
    private Double paidPrice;

    @ApiModelProperty(value = "支付状态：0 未支付 1 已支付")
    private Integer payStatus;

    @ApiModelProperty(value = "订单类型")
    private String orderType;

    @ApiModelProperty(value = "收费时间")
    private Date chageTime;

    @ApiModelProperty(value = "收费员ID")
    private String chargeId;
}
