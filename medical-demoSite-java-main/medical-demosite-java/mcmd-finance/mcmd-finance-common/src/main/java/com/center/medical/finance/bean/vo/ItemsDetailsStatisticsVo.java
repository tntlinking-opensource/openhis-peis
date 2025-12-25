package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目明细统计 返回数据
 */
@Data
public class ItemsDetailsStatisticsVo implements Serializable {
    private static final long serialVersionUID = 7417607457724158398L;

    @Excel(name = "id")
    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "项目名称")
    @ApiModelProperty(value = "项目名称")
    private String examfeeitemName;

    @Excel(name = "项目编号")
    @ApiModelProperty(value = "项目编号")
    private Integer examfeeitemid;

    @Excel(name = "项目名称创建时间")
    @ApiModelProperty(value = "维护时间")
    private Date createdate;

    @Excel(name = "原价(单价)", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "原价")
    private Double unitprice;

    @Excel(name = "成本价(单价)", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "成本价")
    private Double costprice;

    @Excel(name = "人次", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "人次")
    private Long personCount;

    @Excel(name = "实收合计金额", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "实收合计金额")
    private String factpriceSum;

    @Excel(name = "平均单价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "平均单价")
    private String averagePrice;

    @Excel(name = "平均折扣", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "平均折扣")
    private String asAverageDiscount;
}
