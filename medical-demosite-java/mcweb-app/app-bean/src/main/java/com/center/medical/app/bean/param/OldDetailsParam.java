package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取报告pdf地址
 */
@Data
public class OldDetailsParam implements Serializable {
    private static final long serialVersionUID = 8998986810129893154L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "授权码")
    private String authCode;
}
