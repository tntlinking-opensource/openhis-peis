package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 发票(Peisorgreservationreceipt)表实体类
 *
 * @author 路飞船长
 * @since 2022-12-02 11:41:03
 */
@Data
@TableName("md_peisorgreservationreceipt")
@ApiModel(value = "Peisorgreservationreceipt", description = "发票实体类")
public class Peisorgreservationreceipt extends Model<Peisorgreservationreceipt> implements Serializable {
    private static final long serialVersionUID = 947434339026302530L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiParam(hidden = true)
    @TableField(exist = false)
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "优惠价格")
    private Double factprice;

    @ApiModelProperty(value = "发票类型ID")
    private String idReceipttype;

    @ApiModelProperty(value = "是否已出票")
    private Integer fRemitted;

    @ApiModelProperty(value = "出票人")
    private String idRemitter;

    @ApiModelProperty(value = "出票时间")
    private Date remittime;

    @ApiModelProperty(value = "发票号码")
    private String receiptsheetno;

    @ApiModelProperty(value = "已退(没用)")
    private Integer fReturned;

    @ApiModelProperty(value = "换票人用户名")
    private String idReturner;

    @ApiModelProperty(value = "换票时间")
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
    private Integer status;

    @ApiModelProperty(value = "体检类型")
    private Integer tjType;

    @ApiModelProperty(value = "发票抬头")
    private String fptt;

    @ApiModelProperty(value = "换票原因")
    private String ttyy;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date modifydate;

    @ApiModelProperty(value = "发票申请人用户名")
    private String proposer;

    @ApiModelProperty(value = "发表申请时间")
    private Date applicationTime;

    @ApiModelProperty(value = "是否删除：0.正常 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "反审核备注")
    private String unauditNote;

    @ApiModelProperty(value = "反审人")
    private String unauditName;

    @ApiModelProperty(value = "反审核时间")
    private Date unauditDate;

    @ApiModelProperty(value = "换票审核时间")
    private Date returnAuditTime;

    @ApiModelProperty(value = "换票审核人用户名")
    private String returnAuditer;

    @ApiModelProperty(value = "原发票号码")
    private String firstReceiptsheetno;

    @ApiModelProperty(value = "换票申请时间")
    private Date returnApplyTime;

    @ApiModelProperty(value = "换票申请人用户名")
    private String idReturnApplyer;

    @ApiModelProperty(value = "审核人")
    private String uapprove;

    @ApiModelProperty(value = "审核时间")
    private Date uapprovedate;

    @ApiParam(hidden = true)
    @TableField(exist = false)
    @ApiModelProperty(value = "订单价格")
    private String ddjg;


}
