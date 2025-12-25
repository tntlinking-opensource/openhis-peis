package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新老系统集成预约接口取消预约参数
 * @author xhp
 * @since 2024-01-03 10:12
 */
@Data
public class IntegratedReservationCancelParam {
    @ApiModelProperty(value = "预约号")
    private String reservationNo;

    @ApiModelProperty(value = "订单类型：0.个检 1.团检", required = true, position = 0)
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "第三方订单ID")
    private String bizOrderNum;

    @ApiModelProperty(value = "预约失败的原因")
    private String failReason;

    @ApiModelProperty(value = "第三方系统ID：本地传0", required = true)
    private String systemId;

    @ApiModelProperty(value = "机构门店ID", required = true)
    private String hospitalSubId;
}
