package com.center.medical.reception.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 当日所有检查实收费用统计导出数据
 */
@Data
public class SummaryDataVo implements Serializable {
    private static final long serialVersionUID = 7188852826919318158L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "支付方式名称")
    @ApiModelProperty(value = "支付方式名称")
    private String paywayName;

    @Excel(name = "个检",cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "个检")
    private String gejian;

    @Excel(name = "团体",cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "团体")
    private String tuanti;

    @Excel(name = "合计",cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "合计")
    private String heji;
}
