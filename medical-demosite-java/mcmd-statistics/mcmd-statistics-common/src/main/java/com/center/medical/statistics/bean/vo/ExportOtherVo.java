package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 导出其他科室工作量
 */
@Data
public class ExportOtherVo implements Serializable {
    private static final long serialVersionUID = -3692612560785892189L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name="检查日期",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "检查时间")
    private Date rummagerTime;

    @Excel(name = "星期")
    @ApiModelProperty(value = "星期")
    private Integer dayForWeek;

    @Excel(name = "科室")
    @ApiModelProperty(value = "科室")
    private String deptName;

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

    @Excel(name = "普通会员工作量")
    @ApiModelProperty(value = "普通会员工作量")
    private Integer pthy;

    @Excel(name = "VIP工作量")
    @ApiModelProperty(value = "VIP工作量")
    private Integer vip;

    @Excel(name = "VVIP工作量")
    @ApiModelProperty(value = "VVIP工作量")
    private Integer vvip;

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
