package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 费用合计 分页查询 返回数据
 */
@Data
public class FCTotalVo implements Serializable {
    private static final long serialVersionUID = 4874485829644914057L;

    @Excel(name = "收费方式")
    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @Excel(name = "预交金额", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "预交金额")
    private String yjMaoney;

    @Excel(name = "实收金额", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "实收金额")
    private String shMoney;

    @Excel(name = "合计费用", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "合计费用")
    private String total;
}
