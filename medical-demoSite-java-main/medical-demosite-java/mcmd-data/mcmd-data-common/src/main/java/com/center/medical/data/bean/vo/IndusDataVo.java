package com.center.medical.data.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取行业类别代码
 */
@Data
public class IndusDataVo implements Serializable {
    private static final long serialVersionUID = 385740108996212519L;

    @ApiModelProperty(value = "类别名称")
    private String indusTypeName;

    @ApiModelProperty(value = "行业类别小类代码")
    private String indusTypeCode;
}
