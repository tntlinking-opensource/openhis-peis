package com.center.medical.machine.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 当天登记的体检者id和体检号
 */
@Data
public class CurrentDateInfoDto implements Serializable {
    private static final long serialVersionUID = -1441052453603690789L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

}
