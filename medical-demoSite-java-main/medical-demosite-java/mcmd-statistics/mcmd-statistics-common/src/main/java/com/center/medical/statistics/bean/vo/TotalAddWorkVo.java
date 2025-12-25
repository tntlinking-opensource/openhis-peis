package com.center.medical.statistics.bean.vo;

import com.center.medical.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  加项工作量 分页返回数据
 */
@Data
public class TotalAddWorkVo implements Serializable {
    private static final long serialVersionUID = 198072415972739429L;

    @Excel(name = "序号")
    @ApiModelProperty(value = "序号")
    private Integer rownum;

    @Excel(name = "统计日期（从）",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @Excel(name = "统计日期（到）",dateFormat="yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @Excel(name = "加项医生")
    @ApiModelProperty(value = "加项医生")
    private String jxys;

    @ApiModelProperty(value = "id")
    private String id;

    @Excel(name = "加项总价格")
    @ApiModelProperty(value = "价格")
    private Double price;

    @Excel(name = "加项总优惠")
    @ApiModelProperty(value = "实付价格")
    private Double factprice;




}
