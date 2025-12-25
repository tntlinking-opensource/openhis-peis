package com.center.medical.reception.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检卡误操作参数
 */
@Data
public class RemoveCardParam implements Serializable {
    private static final long serialVersionUID = -2481517814260283498L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "版本")
    private Long version;

    @ApiModelProperty(value = "姓名")
    private String patientname;

    @ApiModelProperty(value = "体检卡数据")
    private TJKFormDataParam formdata;
}
