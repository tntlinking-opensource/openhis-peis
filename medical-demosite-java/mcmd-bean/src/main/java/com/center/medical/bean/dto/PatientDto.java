package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: 路飞船长
 * @date: 2024/4/23 09:17
 * @description: 体检者信息
 */
public class PatientDto implements Serializable {
    private static final long serialVersionUID = 7413455915442221740L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "订单号")
    private String numorgresv;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "电话")
    private String phone;

}
