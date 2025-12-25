package com.center.medical.data.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 个人防护用品分页参数
 */
@Data
public class ZyFhclGrPageParam implements Serializable {

    @ApiModelProperty(value = "防护用品")
    private String defendIndividual;

    @ApiModelProperty(value = "防护用品种类")
    private String defendIndividualClass;

    @ApiModelProperty(value = "输入码")
    private String inputCode;
}
