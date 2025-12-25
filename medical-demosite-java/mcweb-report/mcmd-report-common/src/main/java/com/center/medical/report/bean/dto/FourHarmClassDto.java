package com.center.medical.report.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 主要职业病危害因素 数据
 */
@Data
public class FourHarmClassDto implements Serializable {
    private static final long serialVersionUID = 8116663980863313103L;

    @ApiModelProperty(value = "危害因素种类")
    private String harmClass;

    @ApiModelProperty(value = "危害因素名称")
    private String harmName;
}
