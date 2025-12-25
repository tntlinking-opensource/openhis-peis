package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 销售团检统计 分页 返回数据
 */
@Data
public class HSPageVo implements Serializable {
    private static final long serialVersionUID = -2893536674325624740L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "销售经理")
    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @Excel(name = "开单助理")
    @ApiModelProperty(value = "开单助理")
    private String kdzl;

    @Excel(name = "订单号", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "订单号")
    private String ddh;

    @Excel(name = "团体ID", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private String intId;

    @Excel(name = "客户单位名称")
    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;

    @Excel(name = "原价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "原价")
    private Double yj;

    @Excel(name = "实收", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "实收")
    private Double ss;

    @Excel(name = "人数", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "人数")
    private String rs;

    @Excel(name = "折扣", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "折扣")
    private Double zk;

    @Excel(name = "客单价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "客单价")
    private Double kdj;

    @Excel(name = "统收费用", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "统收费用")
    private String tsfy;

    @Excel(name = "加项人数", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "加项人数")
    private String jxrs;

    @Excel(name = "加项费用", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "加项费用")
    private String jxfy;

    @ApiModelProperty(value = "订单id")
    private String id;

    @ApiModelProperty(value = "变动成本率")
    private Double variableCostRate;
}
