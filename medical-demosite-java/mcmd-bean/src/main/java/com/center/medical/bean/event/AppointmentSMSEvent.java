package com.center.medical.bean.event;

import com.center.medical.bean.param.AppointmentSMSParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 预约短信发送
 */
@Data
@AllArgsConstructor
public class AppointmentSMSEvent  implements Serializable {
    private static final long serialVersionUID = -3567883683914970109L;


    @ApiModelProperty(value = "参数")
    private AppointmentSMSParam param;
}
