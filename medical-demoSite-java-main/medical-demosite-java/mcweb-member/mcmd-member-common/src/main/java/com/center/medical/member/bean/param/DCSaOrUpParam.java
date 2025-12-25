package com.center.medical.member.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 科室咨询保存参数
 */
@Data
public class DCSaOrUpParam implements Serializable {
    private static final long serialVersionUID = 8622464004800134972L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "咨询内容")
    private String consultContent;

    @ApiModelProperty(value = "咨询类型，1.现场咨询 2.来电咨询 3.电话回访")
    private Integer consultType;

    @ApiModelProperty(value = "科室id")
    private String depId;

    @ApiModelProperty(value = "体检号")
    private String patientcode;

    @ApiModelProperty(value = "电子签名")
    private String signPath;
}
