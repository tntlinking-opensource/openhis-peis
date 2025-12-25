package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 分页查询发票 返回数据
 */
@Data
public class IRPageVo implements Serializable {
    private static final long serialVersionUID = -5486752812727305192L;

    @Excel(name = "客户单位名称")
    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "体检类型")
    private Integer tjType;


    @ApiModelProperty(value = "发票类型ID")
    private String idReceipttype;

    @Excel(name = "发票类型")
    @ApiModelProperty(value = "发票类型名称")
    private String idReceipttypeName;

    @Excel(name = "发票号")
    @ApiModelProperty(value = "发票号码")
    private String receiptsheetno;

    @Excel(name = "发票抬头")
    @ApiModelProperty(value = "发票抬头")
    private String fptt;

    @Excel(name = "发票面额", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "发票面额")
    private Double receiptcorenumber;

    @Excel(name = "发票时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发表申请时间")
    private Date applicationTime;

    @Excel(name = "申请人")
    @ApiModelProperty(value = "发票申请人用户名")
    private String proposer;

    @Excel(name = "销售经理")
    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @Excel(name = "发票状态", readConverterExp = "0=未审核,1=已审核,2=已出票,3=换票时间,4=换票已审核,5=已换票,6=审核不通过")
    @ApiModelProperty(value = "发票状态")
    private Integer status;

    @Excel(name = "出票人")
    @ApiModelProperty(value = "出票人")
    private String idRemitter;

    @Excel(name = "出票时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "出票时间")
    private Date remittime;

    @Excel(name = "换票人")
    @ApiModelProperty(value = "换票人用户名")
    private String idReturner;

    @Excel(name = "换票原因")
    @ApiModelProperty(value = "换票原因")
    private String ttyy;

    @Excel(name = "换票时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "换票时间")
    private Date returntime;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;

    @Excel(name = "审核人")
    @ApiModelProperty(value = "审核人")
    private String shName;

    @Excel(name = "审核时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "审核时间")
    private Date shTime;

    @Excel(name = "反审核备注")
    @ApiModelProperty(value = "反审核备注")
    private String unauditNote;

    @Excel(name = "反审人")
    @ApiModelProperty(value = "反审人")
    private String unauditName;

    @Excel(name = "反审核时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "反审核时间")
    private Date unauditDate;

    @Excel(name = "原发票号")
    @ApiModelProperty(value = "原发票号码")
    private String firstReceiptsheetno;

    @Excel(name = "换票申请人")
    @ApiModelProperty(value = "换票申请人用户名")
    private String idReturnApplyer;

    @Excel(name = "换票申请时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "换票申请时间")
    private Date returnApplyTime;

    @Excel(name = "换票审核人")
    @ApiModelProperty(value = "换票审核人用户名")
    private String returnAuditer;

    @Excel(name = "换票审核时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "换票审核时间")
    private Date returnAuditTime;


}
