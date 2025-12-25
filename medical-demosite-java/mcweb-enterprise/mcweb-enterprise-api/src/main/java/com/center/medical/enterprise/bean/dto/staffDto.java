package com.center.medical.enterprise.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 人员构成 返回数据
 */
@Data
public class staffDto implements Serializable {
    private static final long serialVersionUID = -7514734419090100973L;


    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "数量")
    private String num;
}
