package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 获取医师数据
 */
@Data
public class GetDoctorDto implements Serializable {
    private static final long serialVersionUID = 5022529255799907452L;

    @ApiModelProperty(value = "检验医师")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String phonenumber;

    @ApiModelProperty(value = "总检时间")
    private String totalTime;

}
