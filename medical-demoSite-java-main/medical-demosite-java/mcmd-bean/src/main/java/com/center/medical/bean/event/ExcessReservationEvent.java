package com.center.medical.bean.event;

import com.center.medical.bean.param.ExcessReservationParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: ay
 * @date: 2023-3-8 20:38
 * @description: 超额预约审批
 */
@Data
@AllArgsConstructor
public class ExcessReservationEvent {

    @ApiModelProperty(value = "参数")
    private ExcessReservationParam param;
}
