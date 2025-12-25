package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 导出pacs科室工作量
 */
@Data
public class ExportPacsVo implements Serializable {
    private static final long serialVersionUID = 5312653498274238359L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name="检查时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "检查时间")
    private Date examdatetime;

    @Excel(name = "星期")
    @ApiModelProperty(value = "星期")
    private Integer dayForWeek;

    @Excel(name = "科室")
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @Excel(name = "收费项目")
    @ApiModelProperty(value = "收费项目名称")
    private String examfeeitemName;

    @Excel(name = "医生")
    @ApiModelProperty(value = "用户名")
    private String uname;

    @Excel(name = "工作量")
    @ApiModelProperty(value = "工作量")
    private Integer gzlTotal;

    @Excel(name = "工作量个人")
    @ApiModelProperty(value = "工作量个人")
    private Integer gzlGr;

    @Excel(name = "工作量团体")
    @ApiModelProperty(value = "工作量团体")
    private Integer gzlTT;

    @Excel(name = "原始金额")
    @ApiModelProperty(value = "原始金额")
    private BigDecimal oriTotal;

    @Excel(name = "原始金额个人")
    @ApiModelProperty(value = "原始金额个人")
    private BigDecimal oriGr;

    @Excel(name = "原始金额团体")
    @ApiModelProperty(value = "原始金额团体")
    private BigDecimal oriTt;

    @Excel(name = "优惠金额")
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal yhTotal;

    @Excel(name = "优惠金额个人")
    @ApiModelProperty(value = "优惠金额个人")
    private BigDecimal yhGr;

    @Excel(name = "优惠金额团体")
    @ApiModelProperty(value = "优惠金额团体")
    private BigDecimal yhTt;


}
