package com.center.medical.reception.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 加急报告分页返回数据
 */
@Data
public class PlusEmergencyVo implements Serializable {
    private static final long serialVersionUID = -1094129749733241088L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检码")
    private String patientcode;

    @ApiModelProperty(value = "体检者名称")
    private String patientname;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "是否加急，1加急")
    private String isjj;

}
