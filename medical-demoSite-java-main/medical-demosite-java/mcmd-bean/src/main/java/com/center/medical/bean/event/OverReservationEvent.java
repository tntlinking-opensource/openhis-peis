package com.center.medical.bean.event;

import com.center.medical.bean.param.OverReservationParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * @author: ay
 * @date: 2023-3-8 20:38
 * @description: 跨级预约审批
 */
@Data
@AllArgsConstructor
public class OverReservationEvent {

    @ApiModelProperty(value = "参数")
    private OverReservationParam param;
}
