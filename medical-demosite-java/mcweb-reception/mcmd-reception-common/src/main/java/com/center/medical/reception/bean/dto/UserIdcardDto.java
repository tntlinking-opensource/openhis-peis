package com.center.medical.reception.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户身份证号 返回数据
 */
@Data
public class UserIdcardDto implements Serializable {
    private static final long serialVersionUID = -8959696669611020033L;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @ApiModelProperty(value = "性别 0男1女")
    private String idSex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "客户证件类型，详见：CusCardType")
    private Integer countreportoccupationxml;

}
