package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 团体结算 导出 数据
 */
@Data
public class TCExportVo implements Serializable {
    private static final long serialVersionUID = 925519663579702751L;

    @Excel(name = "订单号")
    @ApiModelProperty(value = "订单号")
    private String ddh;

    @Excel(name = "团体名称")
    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @Excel(name = "应收金额")
    @ApiModelProperty(value = "应收金额 (弃用：应该是动态计算所有统收已收的金额)")
    private Double ys;

    @Excel(name = "结算金额")
    @ApiModelProperty(value = "实付")
    private Double moneyamountpaid;

    @Excel(name = "待结")
    @ApiModelProperty(value = "待结")
    private Double dj;

    @Excel(name = "支付方式")
    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @Excel(name = "销售经理")
    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @Excel(name = "收费员")
    @ApiModelProperty(value = "收费员")
    private String username;

    @Excel(name = "收费日期",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "收费日期")
    private Date moneyamountpaiddate;

    @Excel(name = "卡号")
    @ApiModelProperty(value = "卡号")
    private String cardno;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String note;


}
