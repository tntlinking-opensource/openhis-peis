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
 * 体检者结算表(MdPeispatientReservationCharge)表实体类
 *
 * @author ay
 * @since 2023-07-17 20:48:13
 */
@Data
@TableName("md_peispatient_reservation_charge")
@ApiModel(value = "MdPeispatientReservationCharge", description = "体检者结算表实体类")
public class MdPeispatientReservationCharge extends Model<MdPeispatientReservationCharge> implements Serializable {
    private static final long serialVersionUID = 707322707403701907L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "订单号")
    private String idOrgreservation;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "记账金额")
    private Double jzje;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "付款方式ID")
    private String idPayway;

    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @ApiModelProperty(value = "收费信息ID")
    private String idCharge;

    @ApiModelProperty(value = "结算日期(弃用)")
    private Date jsdate;
}
