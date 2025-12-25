package com.center.medical.enterprise.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-04-04 11:35
 * @description: AES解密参数
 */
@Data
public class EncryptV2Dto implements Serializable {
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "授权码")
    private String authCode;

    @ApiModelProperty(value = "标识码")
    private String flag;

    @ApiModelProperty(value = "请求数据（RSA非对称加密数据）")
    private String data;

}
