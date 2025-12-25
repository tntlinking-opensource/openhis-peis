package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2024/5/11 10:14
 * @description: 重复体检者信息
 */
@Data
public class PatientDuplicate implements Serializable {
    private static final long serialVersionUID = -5178887595951483102L;
    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "任务分组ID")
    private String idOrgreservationgroup;
}
