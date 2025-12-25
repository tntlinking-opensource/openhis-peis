package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 收费日报-每日记账报表返回数据
 */
@Data
public class EveryDayJZVo implements Serializable {
    private static final long serialVersionUID = -1476294641344317569L;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "体检者姓名")
    private String patientname;

    @ApiModelProperty(value = "团体ID")
    private String idOrg;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "支付方式")
    private String idPayWay;

    @ApiModelProperty(value = "记账金额")
    private String jzje;

    @ApiModelProperty(value = "结算金额(记账已结)")
    private String moneyamountpaid;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "收费员")
    private String idFeecharger;

    @ApiModelProperty(value = "xs")
    private String xs;

    @ApiModelProperty(value = "jzdwr")
    private String jzdwr;

    @ApiModelProperty(value = "记账单位")
    private String jzdw;

    @ApiModelProperty(value = "审批人")
    private String spr;

    @ApiModelProperty(value = "创建日期")
    private String createDate;
}
