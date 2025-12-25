package com.center.medical.finance.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 个检销售统计 分页 返回数据
 */
@Data
public class ISPageVo implements Serializable {
    private static final long serialVersionUID = -8229250172786167704L;

    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "序号")
    private String rownum;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "分中心")
    @ApiModelProperty(value = "分中心")
    private String fzx;

    @Excel(name = "部门")
    @ApiModelProperty(value = "部门名称")
    private String depart;

    @Excel(name = "开单医师")
    @ApiModelProperty(value = "销售经理")
    private String xsjl;


    @Excel(name = "原价合计", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "原价合计")
    private Double yjhj;

    @Excel(name = "实收合计", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "实收合计")
    private Double sshj;

    @Excel(name = "加项原价合计", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "价格")
    private Double addorgprice;

    @Excel(name = "科室加项费用", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "实付价格")
    private Double addprice;

    @Excel(name = "人数", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "数量")
    private Double count;

    @Excel(name = "折扣", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "折扣")
    private String zk;

    @Excel(name = "客单价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "客单价")
    private String kdj;

    @Excel(name = "成本价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "成本价")
    private String costprice;

    @Excel(name = "登记时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date dateregister;

}
