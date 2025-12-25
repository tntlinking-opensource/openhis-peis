package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取体检者分检结论
 */
@Data
public class FindByCodeDhDto implements Serializable {

    private static final long serialVersionUID = -8723984750597767943L;

    @ApiModelProperty(value = "结论词ID")
    private String basconclusionId;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "建议")
    private String suggestion;


}
