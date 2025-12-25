package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 个检销售统计 获取关联的数据 参数
 */
@Data
public class ISListDataVo implements Serializable {
    private static final long serialVersionUID = -3300315423175167126L;

    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "序号")
    private String rownum;

    @Excel(name = "体检号")
    @ApiModelProperty(value = "体检号")
    private String tjh;

    @Excel(name = "个检人员名称")
    @ApiModelProperty(value = "个检人员名称")
    private String gjrymc;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @Excel(name = "原价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "原价")
    private Double yj;

    @Excel(name = "实收", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "实收")
    private Double ss;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @Excel(name = "折扣", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "折扣")
    private String zk;

    @Excel(name="登记时间",dateFormat="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date regDate;

    @ApiModelProperty(value = "体检套餐名称")
    private String tjtcmc;
}
