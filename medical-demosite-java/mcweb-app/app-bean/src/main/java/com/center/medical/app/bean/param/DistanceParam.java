package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 经纬度参数
 */
@Data
public class DistanceParam implements Serializable {
    private static final long serialVersionUID = -5175934154722822825L;

    @ApiModelProperty(value = "经度")
    private double lng;

    @ApiModelProperty(value = "纬度")
    private double lat;

}
