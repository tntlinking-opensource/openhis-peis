package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 平安对账单分页返回数据
 */
@Data
public class PingAnPageVo implements Serializable {
    private static final long serialVersionUID = 1442960810731523206L;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "客户单位团体号")
    private String intId;

    @ApiModelProperty(value = "任务已完成")
    private Integer fFinished;

    @ApiModelProperty(value = "总数(女性体检人数+男性体检人数)")
    private String placeCount;

    @ApiModelProperty(value = "预定：0.不是备单人员 1.备单人员")
    private String bdcount;

    @ApiModelProperty(value = "没登记,1.是 0否")
    private String noreg;

    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private String reg;

    @ApiModelProperty(value = "预定时间")
    private Date ydDate;
}
