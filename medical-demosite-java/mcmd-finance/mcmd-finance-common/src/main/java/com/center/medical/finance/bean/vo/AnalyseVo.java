package com.center.medical.finance.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检结账单数据返回数据
 */
@Data
public class AnalyseVo implements Serializable {
    private static final long serialVersionUID = 6145895208744406127L;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "团体名称")
    private String orgName;

    @ApiModelProperty(value = "任务已完成")
    private String fFinished;

    @ApiModelProperty(value = "总数(女性体检人数+男性体检人数)")
    private String placeCount;

    @ApiModelProperty(value = "预定：0.不是备单人员 1.备单人员")
    private String bdcount;

    @ApiModelProperty(value = "是否已登记：0或null.否 1.是")
    private String reg;

    @ApiModelProperty(value = "没登记,1.是 0否")
    private String noreg;

    @ApiModelProperty(value = "团体任务预定时间")
    private Date ydDate;

    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private String intId;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;


}
