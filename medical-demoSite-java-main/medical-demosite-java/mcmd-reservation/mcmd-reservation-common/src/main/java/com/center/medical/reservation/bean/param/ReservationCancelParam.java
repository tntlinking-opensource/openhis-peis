package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2023/4/8 17:57
 * @description: 预约取消参数
 */
@Data
public class ReservationCancelParam implements Serializable {
    private static final long serialVersionUID = -6719170663566817814L;

    @ApiModelProperty(value = "预约号")
    private String reservationNo;

    @ApiModelProperty(value = "订单类型：0.个检 1.团检", required = true, position = 0)
    private Integer fUsecodehiden;

    @ApiModelProperty(value = "体检号或者订单号")
    private String pcodeOrOrderId;

    @ApiModelProperty(value = "预约失败的原因")
    private String failReason;

    @ApiModelProperty(value = "第三方系统ID：本地传0", required = true)
    private String systemId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "更新者id")
    private String modifier;
}
