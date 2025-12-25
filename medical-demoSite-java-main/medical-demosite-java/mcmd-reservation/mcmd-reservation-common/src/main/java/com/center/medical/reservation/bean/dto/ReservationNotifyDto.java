package com.center.medical.reservation.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-03-28 15:48
 * @description: 预约信息变更待通知数据
 */
@Data
public class ReservationNotifyDto implements Serializable {
    private static final long serialVersionUID = 5642367800485018441L;

    @ApiModelProperty(value = "通知记录ID")
    private String id;

    @ApiModelProperty(value = "第三方预约ID")
    private String bizId;

    @ApiModelProperty(value = "预约状态：-1预约失败 1.待预约 2.已预约 3.预约结束")
    private Integer status;

    @ApiModelProperty(value = "第三方系统ID：本地传0", required = true)
    private String systemId;

    @ApiModelProperty(value = "预约失败的原因")
    private String failReason;
}
