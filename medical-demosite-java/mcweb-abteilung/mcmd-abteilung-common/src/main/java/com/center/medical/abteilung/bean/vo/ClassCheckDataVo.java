package com.center.medical.abteilung.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取类别代号数据
 */
@Data
public class ClassCheckDataVo implements Serializable {
    private static final long serialVersionUID = 5113825372184652764L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String text;
}
