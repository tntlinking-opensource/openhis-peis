package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 团检加弃项禁检、反禁检操作参数
 */
@Data
public class PausedStatusParam implements Serializable {
    private static final long serialVersionUID = -459059987936002156L;

    @ApiModelProperty(value = "0 反禁检 1 禁检")
    private Integer paused;

    @ApiModelProperty(value = "ids")
    private List<String> ids;
}
