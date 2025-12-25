package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PingAnGetOfferParam implements Serializable {
    private static final long serialVersionUID = -4255383745091100193L;

    @ApiModelProperty(value = "医疗机构订单 ID")
    private String hospitalOrderId;

    @ApiModelProperty(value = "appId")
    private String appId;
}
