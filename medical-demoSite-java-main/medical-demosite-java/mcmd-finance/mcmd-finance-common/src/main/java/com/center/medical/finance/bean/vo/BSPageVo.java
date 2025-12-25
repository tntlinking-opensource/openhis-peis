package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 记账管理-银行汇款结算 分页 返回数据
 */
@Data
public class BSPageVo implements Serializable {
    private static final long serialVersionUID = -2188629475530553308L;

    @ApiModelProperty(value = "对方户名")
    private String remitter;

    @ApiModelProperty(value = "收入金额")
    private String income;

    @ApiModelProperty(value = "交易日期")
    private Date transDate;

    @ApiModelProperty(value = "对方账户编号")
    private String transactionnumber;

    @ApiModelProperty(value = "结算金额")
    private Double jsje;

    @ApiModelProperty(value = "是否结算")
    private String isJs;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "是否审核")
    private String isSh;

    @ApiModelProperty(value = "结算时间")
    private Date jssj;

    @ApiModelProperty(value = "审核时间")
    private Date shsj;
}
