package com.center.medical.reservation.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取待上传平安数据
 */
@Data
public class GetPingAnCodeDto implements Serializable {
    private static final long serialVersionUID = -6811400450161690973L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

}
