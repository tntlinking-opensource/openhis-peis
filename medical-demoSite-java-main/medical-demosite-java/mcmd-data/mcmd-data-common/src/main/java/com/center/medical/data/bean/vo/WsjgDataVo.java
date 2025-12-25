package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 外送机构下拉
 */
@Data
public class WsjgDataVo implements Serializable {
    private static final long serialVersionUID = 5219678400709493230L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "外送机构")
    private String wsjg;
}
