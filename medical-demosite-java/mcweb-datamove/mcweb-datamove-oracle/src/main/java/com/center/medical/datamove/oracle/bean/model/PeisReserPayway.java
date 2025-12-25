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
 * 体检者结算方式表(PeisReserPayway)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:01
 */
@Data
@TableName("PEIS_RESER_PAYWAY")
@ApiModel(value = "PeisReserPayway", description = "体检者结算方式表实体类")
public class PeisReserPayway extends Model<PeisReserPayway> implements Serializable {
    private static final long serialVersionUID = -22882891091830656L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检者结算表ID")
    private String idCharge;

    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "结算日期")
    private Date moneyamountpaiddate;

    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @ApiModelProperty(value = "付款方式ID")
    private String idPayway;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "体检卡卡号")
    private String cardno;

    @ApiModelProperty(value = "已收费")
    private Integer isCharged;

    @ApiModelProperty(value = "${column.comment}")
    private String billingAccount;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isReceipt;
}
