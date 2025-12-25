package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约来检情况分析 分页参数
 */
@Data
public class ReAnalysisParam implements Serializable {
    private static final long serialVersionUID = -5920034724459915282L;

    @ApiModelProperty(value = "分中心id（单选）")
    private String branchId;


    @ApiModelProperty(value = "客户单位名称")
    private String khdwmc;


    @ApiModelProperty(value = "团体ID")
    private String intId;


    @ApiModelProperty(value = "预约日期")
    private Date reservationDate;

}
