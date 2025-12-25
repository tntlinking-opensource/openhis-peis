package com.center.medical.app.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-04-04 11:35
 * @description: 取消预约的数据信息
 */
@Data
public class CancelAppointmentDto implements Serializable {
    private static final long serialVersionUID = -239950325463680105L;

    @ApiModelProperty(value = "预约ID")
    @NotNull(message = "预约ID不能为空！")
    private String id;

    @ApiModelProperty(value = "取消原因")
    @NotNull(message = "取消原因不能为空！")
    private String reason;
}
