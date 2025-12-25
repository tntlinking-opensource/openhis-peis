package com.center.medical.olddata.bean.model;


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
 * 体检者缴费表(Peispatientcharge)表实体类
 *
 * @author ay
 * @since 2023-11-10 14:59:30
 */
@Data
@TableName("PEISPATIENTCHARGE")
@ApiModel(value = "Peispatientcharge", description = "体检者缴费表实体类")
public class OrPeispatientcharge extends Model<OrPeispatientcharge> implements Serializable {
    private static final long serialVersionUID = -83035580029091952L;

    @ApiModelProperty(value = "ID_PATIENTCHARGE")
    private String idPatientcharge;


    @ApiModelProperty(value = "ID_PATIENTCHARGESHEET")
    private String idPatientchargesheet;


    @ApiModelProperty(value = "体检号(ID)")
    private String patientcode;


    @ApiModelProperty(value = "支付方式ID")
    private String idPayway;


    @ApiModelProperty(value = "支付方式")
    private String payway;


    @ApiModelProperty(value = "金额应付")
    private Double moneyamount;


    @ApiModelProperty(value = "金额实付")
    private Double moneyamountpaid;


    @ApiModelProperty(value = "(内部使用)")
    private Integer fWorkByorgdelta;


    @ApiModelProperty(value = "金额已接口")
    private Integer fFeetransfered;


    @ApiModelProperty(value = "收费确认")
    private Integer fFeeconfirmed;


    @ApiModelProperty(value = "已收费")
    private Integer fFeecharged;


    @ApiModelProperty(value = "F_FEECHARGEDINTTRANS")
    private Integer fFeechargedinttrans;


    @ApiModelProperty(value = "已收费(记帐)")
    private Integer fFeechargedoncredit;


    @ApiModelProperty(value = "为退费")
    private Integer fIsreturn;


    @ApiModelProperty(value = "发票已打印")
    private Integer fReceiptprinted;


    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;


    @ApiModelProperty(value = "缴费时间")
    private Date feechargetime;


    @ApiModelProperty(value = "备注")
    private String note;


    @ApiModelProperty(value = "行版本")
    private Integer verThisrecord;


    @ApiModelProperty(value = "内部使用")
    private Integer fWorkInnerModify;


    @ApiModelProperty(value = "LEAGMEMBERCARDNO")
    private String leagmembercardno;


    @ApiModelProperty(value = "ID_EXAMPLACE")
    private String idExamplace;


    @ApiModelProperty(value = "RECEIPTSHEETNOFROMINTERFACE")
    private String receiptsheetnofrominterface;


    @ApiModelProperty(value = "F_FEECHARGEDBYINTERFACE")
    private Integer fFeechargedbyinterface;


    @ApiModelProperty(value = "WHOFEECHARGEDBYINTERFACE")
    private String whofeechargedbyinterface;


    @ApiModelProperty(value = "INTPATIENTCHARGE_CODE")
    private String intpatientchargeCode;


    @ApiModelProperty(value = "INTPATIENTCHARGE_DATE")
    private Date intpatientchargeDate;


    @ApiModelProperty(value = "0:未结清 1：结清")
    private Integer isJq;


    @ApiModelProperty(value = "0:不是统收1：统收")
    private Integer isTotalcharge;


    @ApiModelProperty(value = "体检卡卡号")
    private String cardno;


    @ApiModelProperty(value = "0:不删除 1：删除")
    private Integer isDelete;


    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;


    @ApiModelProperty(value = "创建时间")
    private Date createdate;


    @ApiModelProperty(value = "修改日期")
    private Date modifydate;


    @ApiModelProperty(value = "顺序")
    private Integer numIndex;


    @ApiModelProperty(value = "${column.comment}")
    private Integer shortCode;


    @ApiModelProperty(value = "${column.comment}")
    private Integer isAdd;


    @ApiModelProperty(value = "订单号")
    private String tradeNo;


    @ApiModelProperty(value = "主单版本（用于统一最新版本）")
    private String chargeMainVersion;


    @ApiModelProperty(value = "主表ID")
    private String chargeMainId;


    @ApiModelProperty(value = "支付平台交易流水号")
    private String txTradeNo;

}
