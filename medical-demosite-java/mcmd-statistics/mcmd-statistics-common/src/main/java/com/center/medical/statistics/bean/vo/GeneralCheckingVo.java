package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 工作量统计-总检工作量 分页返回数据
 */
@Data
public class GeneralCheckingVo implements Serializable {
    private static final long serialVersionUID = 6623874877151753659L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "医生ID")
    private String doctorId;

    @Excel(name = "医生姓名")
    @ApiModelProperty(value = "医生姓名")
    private String doctorname;

    @Excel(name="检查时间",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "检查时间")
    private Date examTime;

    @Excel(name = "星期")
    @ApiModelProperty(value = "星期")
    private Integer dayForWeek;

    @Excel(name = "总工作量")
    @ApiModelProperty(value = "总工作量")
    private Integer total;

    @Excel(name = "工作量团体")
    @ApiModelProperty(value = "工作量团体")
    private Integer org;

    @Excel(name = "工作量个人")
    @ApiModelProperty(value = "工作量个人")
    private Integer personnel;

    @Excel(name = "普通会员工作量")
    @ApiModelProperty(value = "普通会员工作量")
    private Integer common;

    @Excel(name = "vip工作量")
    @ApiModelProperty(value = "vip工作量")
    private Integer vip;

    @Excel(name = "vvip工作量")
    @ApiModelProperty(value = "vvip工作量")
    private Integer vvip;


}
