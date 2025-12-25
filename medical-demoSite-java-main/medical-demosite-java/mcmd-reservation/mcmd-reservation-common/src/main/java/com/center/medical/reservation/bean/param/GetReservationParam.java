package com.center.medical.reservation.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取预约详情
 */
@Data
public class GetReservationParam implements Serializable {
    private static final long serialVersionUID = -8582648588813701251L;


    @ApiModelProperty(value = "预约号")
    private String reservationNo;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "预约手机号")
    private String mobile;

}
