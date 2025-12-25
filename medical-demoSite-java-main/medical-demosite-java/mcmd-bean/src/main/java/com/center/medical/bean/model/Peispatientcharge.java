package com.center.medical.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检者缴费表(Peispatientcharge)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:10
 */
@Data
@TableName("md_peispatientcharge")
@ApiModel(value = "Peispatientcharge", description = "体检者缴费表实体类")
public class Peispatientcharge extends Model<Peispatientcharge> implements Serializable {
    private static final long serialVersionUID = -19870190176138709L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "充值id")
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

    @ApiModelProperty(value = "已收费：0.未收费 1.已收费")
    private Integer fFeecharged;

    @ApiModelProperty(value = "下载标志  0或NULL未下载   1已下载 2更新")
    private Integer fFeechargedinttrans;

    @ApiModelProperty(value = "已收费(记帐)")
    private Integer fFeechargedoncredit;

    @ApiModelProperty(value = "未退费")
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

    @ApiModelProperty(value = "是否复查额度消费  1是null不是")
    private String receiptsheetnofrominterface;

    @ApiModelProperty(value = "上传标志  1已上传    其他 未上传")
    private Integer fFeechargedbyinterface;

    @ApiModelProperty(value = "是否已上传bigdata线上大数据报表  1是  0/null否")
    private String whofeechargedbyinterface;

    @ApiModelProperty(value = "INTPATIENTCHARGE_CODE")
    private String intpatientchargeCode;

    @ApiModelProperty(value = "INTPATIENTCHARGE_DATE")
    private Date intpatientchargeDate;

    @ApiModelProperty(value = "是否结清：0:未结清 1：结清")
    private Integer isJq;

    @ApiModelProperty(value = "是否统收：0:不是统收1：统收")
    private Integer isTotalcharge;

    @ApiModelProperty(value = "体检卡卡号")
    private String cardno;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "顺序")
    private Integer numIndex;

    @ApiModelProperty(value = "体检短号")
    private Integer shortCode;

    @ApiModelProperty(value = "是否是加项收费   1 是  null不是")
    private Integer isAdd;

    @ApiModelProperty(value = "订单号")
    private String tradeNo;

    @ApiModelProperty(value = "主单版本（用于统一最新版本）")
    private String chargeMainVersion;

    @ApiModelProperty(value = "主表ID")
    private String chargeMainId;

    @ApiModelProperty(value = "支付平台交易流水号")
    private String txTradeNo;

    @ApiModelProperty(value = "是否可以编辑卡号：0.可以 1.不可以")
    @TableField(exist = false)
    private Integer isChange;

    @TableField(exist = false)
    @ApiModelProperty(value = "_uid：顺序号")
    private Integer _uid;

    @TableField(exist = false)
    @ApiModelProperty(value = "操作标识：{removed:移除，modified:修改，added：新增}")
    private String _state;

    @ApiModelProperty(value = "统收：0.否 1.是")
    @TableField(exist = false)
    private Integer tong;

    @ApiModelProperty(value = "是否卡收：0.否 1.是")
    @TableField(exist = false)
    private Integer kashou;

    @TableField(exist = false)
    @ApiModelProperty(value = "收费员名称")
    private String feechargerName;

}
