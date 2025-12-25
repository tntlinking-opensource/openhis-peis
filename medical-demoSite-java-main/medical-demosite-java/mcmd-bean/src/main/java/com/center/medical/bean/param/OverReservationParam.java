package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: ay
 * @date: 2023-3-8 20:38
 * @description: 跨级预约参数
 */
@Data
public class OverReservationParam implements Serializable {
    private static final long serialVersionUID = -2715101696008153021L;

    @ApiModelProperty(value = "实例ID")
    private String id;

    @ApiModelProperty(value = "状态：-1.失效 0.等待开始 1.进行中 2.已通过 3.被驳回")
    private Integer status;
}
