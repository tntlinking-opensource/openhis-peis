package com.center.medical.system.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改开单医生 参数
 */
@Data
public class UpdateOpenDoctorParam implements Serializable {
    private static final long serialVersionUID = 7132456772287129708L;


    @ApiModelProperty(value = "开单医生名字")
    private String userName;


    @ApiModelProperty(value = "体检号")
    private String patientcode;
}
