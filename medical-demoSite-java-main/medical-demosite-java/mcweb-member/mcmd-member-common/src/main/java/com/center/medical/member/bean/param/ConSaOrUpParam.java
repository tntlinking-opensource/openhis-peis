package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 咨询与随访保存参数
 */
@Data
public class ConSaOrUpParam implements Serializable {
    private static final long serialVersionUID = -7049071290165011378L;


    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "咨询人姓名")
    private String consultName;

    @ApiModelProperty(value = "咨询电话")
    private String consultPhone;

    @ApiModelProperty(value = "咨询类型，1.现场咨询 2.来电咨询 3.电话回访")
    private Integer consultType;

    @ApiModelProperty(value = "咨询内容")
    private String consultContent;
}
