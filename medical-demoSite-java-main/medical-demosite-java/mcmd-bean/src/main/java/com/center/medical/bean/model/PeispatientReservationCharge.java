package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者结算表(PeispatientReservationCharge)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:09
 */
@Data
@TableName("md_peispatient_reservation_charge")
@ApiModel(value = "PeispatientReservationCharge", description = "体检者结算表实体类")
public class PeispatientReservationCharge extends Model<PeispatientReservationCharge> implements Serializable {
    private static final long serialVersionUID = -62902525716490970L;

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
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "付款方式ID")
    private String idPayway;

    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @ApiModelProperty(value = "收费信息ID")
    private String idCharge;

    @ApiModelProperty(value = "结算日期(弃用)")
    private Date jsdate;

    @TableField(exist = false)
    @ApiModelProperty(value = "姓名")
    private String name;

    @TableField(exist = false)
    @ApiModelProperty(value = "公司名称")
    private String orgName;
}
