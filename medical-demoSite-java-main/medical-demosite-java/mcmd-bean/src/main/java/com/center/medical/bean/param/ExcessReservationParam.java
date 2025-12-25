package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: ay
 * @date: 2023-3-8 20:38
 * @description: 超额预约审批参数
 */
@Data
public class ExcessReservationParam implements Serializable {
    private static final long serialVersionUID = -5374047357320014958L;

    @ApiModelProperty(value = "json数据")
    private String data;

}
