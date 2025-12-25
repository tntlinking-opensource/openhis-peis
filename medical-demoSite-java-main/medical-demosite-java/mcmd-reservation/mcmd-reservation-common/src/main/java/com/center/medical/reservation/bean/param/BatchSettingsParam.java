package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 批量设置 参数
 */
@Data
public class BatchSettingsParam implements Serializable {
    private static final long serialVersionUID = -290232985931814678L;


    @ApiModelProperty(value = "预约类型id")
    private Integer levelId;


    @ApiModelProperty(value = "预约人数")
    private Integer num;


    @ApiModelProperty(value = "状态：0.关闭 1.开放 2.过期")
    private Integer status;
}
