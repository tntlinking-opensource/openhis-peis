package com.center.medical.finance.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 银行汇款结算-保存 上方汇款单位数据
 */
@Data
public class BRFormDataDto implements Serializable {
    private static final long serialVersionUID = -876133915389970478L;

    @ApiModelProperty(value = "对方账户编号")
    private String transactionnumber;

    @ApiModelProperty(value = "对方户名")
    private String remitter;

    @ApiModelProperty(value = "交易日期")
    private Date transdate;

    @ApiModelProperty(value = "收入金额")
    private String income;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;

}
