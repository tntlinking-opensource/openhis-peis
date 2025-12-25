package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 路飞
 * @date: 2023-03-21 14:46
 * @description: 团体预约列表查询参数
 */
@Data
@ApiModel(value = "预约设置列表查询参数")
public class RgListParam implements Serializable {
    private static final long serialVersionUID = -3654438854782220370L;

    @ApiModelProperty(value = "开始预约日期")
    private Date startDate;

    @ApiModelProperty(value = "截至预约日期")
    private Date endDate;

    @ApiModelProperty(value = "订单名称")
    private String ddmc;

    @ApiModelProperty(value = "预约类型ID")
    private Integer levelId;

    @ApiModelProperty(value = "分中心ID")
    private String branchId;
}
