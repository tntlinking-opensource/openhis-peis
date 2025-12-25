package com.center.medical.statistics.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建 职业健康检查复查人员名单
 */
@Data
public class CreateReviewGroupParam implements Serializable {
    private static final long serialVersionUID = -7168691917921609524L;

    @ApiModelProperty(value = "客户ID")
    private String customerId;

    @ApiModelProperty(value = "订单号")
    private String ddh;

    @ApiModelProperty(value = "开始时间,带着时分秒 00:00:00")
    private Date startDate;

    @ApiModelProperty(value = "结束时间,带着时分秒 23:59:59")
    private Date endDate;

    @ApiModelProperty(value = "分中心ID")
    private String fzxId;
}
