package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 个人防护用品分页返回数据
 */
@Data
public class ZyFhclGrPageVo implements Serializable {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "防护用品代码")
    private String defendIndividualCode;

    @ApiModelProperty(value = "防护用品")
    private String defendIndividual;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "防护用品种类")
    private String defendIndividualClass;
}
