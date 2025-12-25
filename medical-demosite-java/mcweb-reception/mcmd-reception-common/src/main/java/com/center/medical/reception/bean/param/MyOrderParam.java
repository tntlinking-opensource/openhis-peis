package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 我的订单参数
 */
@Data
public class MyOrderParam implements Serializable {
    private static final long serialVersionUID = -7382798761254142366L;

    @ApiModelProperty(value = "手机号（不用传）")
    private String phone;

    @ApiModelProperty(value = "状态 1待预约,2已预约,3体检中,4体检结束")
    private String status;

    @ApiModelProperty(value = "每页显示条数")
    private long size;

    @ApiModelProperty(value = "当前页")
    private long current;
}
