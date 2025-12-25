package com.center.medical.enterprise.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-04-04 14:59
 * @description: 授权数据信息封装类
 */
@Data
public class CommonDto implements Serializable {
    private static final long serialVersionUID = -8409171780121640809L;

    @ApiModelProperty(value = "授权码", required = true)
    private String authCode;

    @ApiModelProperty(value = "标识码")
    private String flag;

    @ApiModelProperty(value = "请求数据（RSA非对称加密数据）")
    private String encryptData;

    public CommonDto(String authCode, String flag, String encryptData) {
        this.authCode = authCode;
        this.flag = flag;
        this.encryptData = encryptData;
    }
}
