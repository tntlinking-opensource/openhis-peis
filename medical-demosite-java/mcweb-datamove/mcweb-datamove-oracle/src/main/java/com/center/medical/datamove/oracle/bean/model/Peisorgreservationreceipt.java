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
 * QT发票表(Peisorgreservationreceipt)表实体类
 *
 * @author ay
 * @since 2023-07-18 09:23:07
 */
@Data
@TableName("PEISORGRESERVATIONRECEIPT")
@ApiModel(value = "Peisorgreservationreceipt", description = "QT发票表实体类")
public class Peisorgreservationreceipt extends Model<Peisorgreservationreceipt> implements Serializable {
    private static final long serialVersionUID = -67474197582663411L;

    @TableId(value = "ID", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "优惠价格")
    private Double factprice;

    @ApiModelProperty(value = "发票类型ID")
    private String idReceipttype;

    @ApiModelProperty(value = "已出票")
    private Integer fRemitted;

    @ApiModelProperty(value = "出票人ID")
    private String idRemitter;

    @ApiModelProperty(value = "出票时间")
    private Date remittime;

    @ApiModelProperty(value = "发票号码")
    private String receiptsheetno;

    @ApiModelProperty(value = "已退")
    private Integer fReturned;

    @ApiModelProperty(value = "退票人ID")
    private String idReturner;

    @ApiModelProperty(value = "退票时间")
    private Date returntime;

    @ApiModelProperty(value = "子团体ID")
    private String idOrgbranch;

    @ApiModelProperty(value = "发票团体名称")
    private String receiptOrgName;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "RECEIPTCOREDATE")
    private Date receiptcoredate;

    @ApiModelProperty(value = "发票面额")
    private Double receiptcorenumber;

    @ApiModelProperty(value = "发票状态")
    private String status;

    @ApiModelProperty(value = "体检类型")
    private String tjType;

    @ApiModelProperty(value = "发票抬头")
    private String fptt;

    @ApiModelProperty(value = "退票原因")
    private String ttyy;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "发票申请人ID")
    private String proposer;

    @ApiModelProperty(value = "发表申请时间")
    private Date applicationTime;

    @ApiModelProperty(value = "${column.comment}")
    private Integer isDelete;

    @ApiModelProperty(value = "${column.comment}")
    private String unauditNote;

    @ApiModelProperty(value = "${column.comment}")
    private String unauditName;

    @ApiModelProperty(value = "${column.comment}")
    private Date unauditDate;

    @ApiModelProperty(value = "${column.comment}")
    private Date returnAuditTime;

    @ApiModelProperty(value = "${column.comment}")
    private String returnAuditer;

    @ApiModelProperty(value = "${column.comment}")
    private String firstReceiptsheetno;

    @ApiModelProperty(value = "${column.comment}")
    private Date returnApplyTime;

    @ApiModelProperty(value = "${column.comment}")
    private String idReturnApplyer;

    @ApiModelProperty(value = "${column.comment}")
    private String uapprove;

    @ApiModelProperty(value = "${column.comment}")
    private Date uapprovedate;
}
