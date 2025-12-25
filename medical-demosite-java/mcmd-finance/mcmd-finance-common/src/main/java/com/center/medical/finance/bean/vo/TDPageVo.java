package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 记账管理-记账结算明细 分页返回数据
 */
@Data
public class TDPageVo implements Serializable {
    private static final long serialVersionUID = 5741342449500440315L;


    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "团体名称")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String name;

    @Excel(name = "结算金额")
    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @Excel(name = "付款方式")
    @ApiModelProperty(value = "付款方式")
    private String paywayName;

    @Excel(name = "收费员")
    @ApiModelProperty(value = "收费员")
    private String idFeecharger;

    @Excel(name = "结算收费时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "收费日期")
    private Date moneyamountpaiddate;

    @Excel(name = "记账时间")
    @ApiModelProperty(value = "缴费时间")
    private String feechargetime;

    @Excel(name = "卡号")
    @ApiModelProperty(value = "体检卡卡号")
    private String cardno;

    @Excel(name = "开单医生")
    @ApiModelProperty(value = "开单医生")
    private String doctorapply;

    @Excel(name = "上次团体开单医师")
    @ApiModelProperty(value = "上次团体开单医师")
    private String lastJlmc;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;


}
