package com.center.medical.enterprise.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取报告加密使用的授权码，公钥私钥
 */
@Data
public class ReportConfig implements Serializable {
    private static final long serialVersionUID = -8845817516165345917L;

    @ApiModelProperty(value = "授权码")
    private String authCode;

    @ApiModelProperty(value = "公钥")
    private String publicKey;

    @ApiModelProperty(value = "私钥")
    private String privateKey;

    @ApiModelProperty(value = "状态，0正常1停用")
    private String status;

}
