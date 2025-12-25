package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 团体结算保存 参数
 */
@Data
public class TCGridDataDto implements Serializable {
    private static final long serialVersionUID = 1940435795839017870L;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "修改日期")
    private Date modifydate;

    @ApiModelProperty(value = "体检者结算表ID")
    private String idCharge;

    @ApiModelProperty(value = "结算日期")
    private Date moneyamountpaiddate;

    @ApiModelProperty(value = "体检卡卡号")
    private String cardno;

    @ApiModelProperty(value = "结算金额")
    private Double moneyamountpaid;

    @ApiModelProperty(value = "收费员ID")
    private String idFeecharger;

    @ApiModelProperty(value = "已收费")
    private Integer isCharged;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "付款方式ID")
    private String idPayway;

    @ApiModelProperty(value = "创建时间")
    private Date createdate;

    @ApiModelProperty(value = "操作标识：{removed:移除，modified:修改，added：新增}")
    private String state;
}
