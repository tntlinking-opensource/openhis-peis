package com.center.medical.statistics.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 批量职业健康检查复查通知书 分页参数
 */
@Data
public class GroupReviewNoticeParam implements Serializable {
    private static final long serialVersionUID = -4569506305887801586L;

    @ApiModelProperty(value = "客户ID")
    private String customerId;


    @ApiModelProperty(value = "订单号")
    private String ddh;
}
