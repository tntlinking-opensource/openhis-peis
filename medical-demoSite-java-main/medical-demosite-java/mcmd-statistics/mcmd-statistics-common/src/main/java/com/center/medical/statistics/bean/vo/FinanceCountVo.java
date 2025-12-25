package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检费用统计-收费明细 返回数据
 */
@Data
public class FinanceCountVo implements Serializable {
    private static final long serialVersionUID = 8750863143014023459L;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "金额应付")
    private Double moneyamount;

    @Excel(name = "收费方式")
    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @Excel(name="收费时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "缴费时间")
    private Date feechargetime;

    @Excel(name = "是否预收" ,readConverterExp = "0=否,1=是")
    @ApiModelProperty(value = "是否预收")
    private String sfys;

    @Excel(name = "预交金额", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "预交金额")
    private String ysje;

    @Excel(name = "实收金额", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "实收金额")
    private String ssmoney;

    @Excel(name="实际费用产生时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "实际费用产生时间")
    private Date sstime;
}
