package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页团检费用统计 返回数据
 */
@Data
public class PersonalTotalVo implements Serializable {
    private static final long serialVersionUID = -8014753043335354602L;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "销售经理")
    @ApiModelProperty(value = "售卡人姓名")
    private String sellName;

    @Excel(name = "开单助理")
    @ApiModelProperty(value = "开单助理")
    private String kdzl;

    @Excel(name = "团体ID")
    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private String intId;

    @Excel(name = "体检单位")
    @ApiModelProperty(value = "公司")
    private String orgName;

    @ApiModelProperty(value = "男数量")
    private String mans;

    @ApiModelProperty(value = "女数量")
    private String womans;

    @Excel(name = "原价", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "价格")
    private Double price;

    @Excel(name = "实收", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "实收")
    private Double fastprice;

    @Excel(name = "加项原价合计", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "加项原价合计")
    private Double addorgprice;

    @Excel(name = "科室加项金额", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "科室加项金额")
    private Double addprice;

    @Excel(name = "人数", cellType = Excel.ColumnType.NUMERIC)
    @ApiModelProperty(value = "人数")
    private Double counts;

    @ApiModelProperty(value = "体检号数量")
    private String patientcodeCount;

    @Excel(name = "折扣")
    @ApiModelProperty(value = "折扣")
    private String zk;

    @Excel(name = "客单价")
    @ApiModelProperty(value = "客单价")
    private String kdj;

    @Excel(name = "成本价")
    @ApiModelProperty(value = "成本价")
    private String costprice;
}
