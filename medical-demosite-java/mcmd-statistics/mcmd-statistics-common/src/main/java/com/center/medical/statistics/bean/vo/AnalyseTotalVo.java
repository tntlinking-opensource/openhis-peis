package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 总检医生工作量统计汇总 返回数据
 */
@Data
public class AnalyseTotalVo implements Serializable {
    private static final long serialVersionUID = -7611932080270154779L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @ApiModelProperty(value = "医生ID")
    private String doctorId;

    @Excel(name = "医生名字")
    @ApiModelProperty(value = "医生名字")
    private String doctorName;

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
