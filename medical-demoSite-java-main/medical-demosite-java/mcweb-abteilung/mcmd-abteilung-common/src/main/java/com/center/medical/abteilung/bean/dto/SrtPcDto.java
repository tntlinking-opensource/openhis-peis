package com.center.medical.abteilung.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2022-12-08 18:02
 * @description: 体检者分检结论
 */
@Data
public class SrtPcDto implements Serializable {
    private static final long serialVersionUID = -6126006572529685999L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "科室ID")
    private String divisionId;

    @ApiModelProperty(value = "结论词ID")
    private String basconclusionId;
}
