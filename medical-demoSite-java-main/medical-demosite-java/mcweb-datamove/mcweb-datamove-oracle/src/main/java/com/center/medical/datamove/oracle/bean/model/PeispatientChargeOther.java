package com.center.medical.datamove.oracle.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (PeispatientChargeOther)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:15
 */
@Data
@TableName("PEISPATIENT_CHARGE_OTHER")
@ApiModel(value = "PeispatientChargeOther", description = "$tableInfo.comment实体类")
public class PeispatientChargeOther extends Model<PeispatientChargeOther> implements Serializable {
    private static final long serialVersionUID = -34169024197109130L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
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
    private String price;

    @ApiModelProperty(value = "支付金额")
    private String paidPrice;

    @ApiModelProperty(value = "支付状态")
    private String payStatus;

    @ApiModelProperty(value = "订单类型")
    private String orderType;

    @ApiModelProperty(value = "收费时间")
    private Date chageTime;

    @ApiModelProperty(value = "收费员ID")
    private String chargeId;
}
