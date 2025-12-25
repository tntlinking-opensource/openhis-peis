package com.center.medical.statistics.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 异常人数
 */
@Data
public class YcrsDto implements Serializable {
    private static final long serialVersionUID = 7396160472986384062L;

    @ApiModelProperty(value = "危害因素分类分类")
    private String healthEvaluationClass;

    @ApiModelProperty(value = "type02")
    private String type02;

    @ApiModelProperty(value = "type12")
    private String type12;

    @ApiModelProperty(value = "type11")
    private String type11;

    @ApiModelProperty(value = "type13")
    private String type13;

    @ApiModelProperty(value = "type21")
    private String type21;

    @ApiModelProperty(value = "type23")
    private String type23;

    @ApiModelProperty(value = "type41")
    private String type41;

    @ApiModelProperty(value = "type43")
    private String type43;

    @ApiModelProperty(value = "type31")
    private String type31;

    @ApiModelProperty(value = "type33")
    private String type33;
}
