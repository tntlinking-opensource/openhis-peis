package com.center.medical.bean.event;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 删除预约信息
 */
@Data
@AllArgsConstructor
public class DelReservationEvent implements Serializable {
    private static final long serialVersionUID = 1741280190589047806L;


    @ApiModelProperty(value = "体检号")
    private String patientCode;
}
