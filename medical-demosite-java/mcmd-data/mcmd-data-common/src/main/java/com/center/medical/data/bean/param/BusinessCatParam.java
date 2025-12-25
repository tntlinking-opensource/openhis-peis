package com.center.medical.data.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 业务类型分页参数
 */
@Data
public class BusinessCatParam implements Serializable {
    private static final long serialVersionUID = -8519519252238776708L;

    @ApiModelProperty(value = "输入码")
    private String inputCode;


    @ApiModelProperty(value = "种类名称")
    private String typeName;
}
