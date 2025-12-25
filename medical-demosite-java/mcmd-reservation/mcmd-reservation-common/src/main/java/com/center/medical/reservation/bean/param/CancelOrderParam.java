package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 平安好医生 取消申请参数
 */
@Data
public class CancelOrderParam implements Serializable {
    private static final long serialVersionUID = -3599459806278545574L;

    @ApiModelProperty(value = "医疗机构订单 ID")
    private String hospitalOrderId;

    @ApiModelProperty(value = "体检平台订单 ID")
    private String orderId;

    @ApiModelProperty(value = "appId")
    private String appId;

}
