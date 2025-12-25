package com.center.medical.sellcrm.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询订单和任务错误的分组
 */
@Data
public class VationAndGroupErrorDataDto implements Serializable {
    private static final long serialVersionUID = 728997487288625733L;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "任务id")
    private String idOrgreservation;

    @ApiModelProperty(value = "分中心")
    private String hospitalcode;
}
