package com.center.medical.outreach.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-04-04 14:59
 * @description: 接收加密数据公共类
 */
@Data
public class CommonDto implements Serializable {
    private static final long serialVersionUID = -8409171780121640809L;

    @ApiModelProperty(value = "授权码")
    private String authCode;

    @ApiModelProperty(value = "标识码")
    private String flag;

    @ApiModelProperty(value = "请求数据（RSA非对称加密数据）")
    private String encryptData;
}
