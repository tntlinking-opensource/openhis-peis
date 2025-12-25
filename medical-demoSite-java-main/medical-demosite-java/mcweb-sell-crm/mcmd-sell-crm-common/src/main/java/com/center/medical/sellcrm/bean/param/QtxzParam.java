package com.center.medical.sellcrm.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 前台须知
 */
@Data
public class QtxzParam implements Serializable {
    private static final long serialVersionUID = 8555310077747981411L;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "前台须知")
    private String qtxz;
}
