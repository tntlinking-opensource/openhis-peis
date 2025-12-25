package com.center.medical.finance.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * RSA非对称加密配置
 */
@Data
public class RsaParam implements Serializable {
    private static final long serialVersionUID = -4570662743811405608L;

    @ApiModelProperty(value = "加密数据")
    private String data;

    @ApiModelProperty(value = "授权码")
    private String authCode;


    public RsaParam() {
    }

    public RsaParam(String data, String authCode) {
        this.data = data;
        this.authCode = authCode;
    }
}
