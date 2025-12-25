package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 申请预约平安
 */
@Data
public class ApplyForOrderParam implements Serializable {
    private static final long serialVersionUID = 2649397717970605543L;

    @ApiModelProperty(value = "加密数据")
    private String data;


}
