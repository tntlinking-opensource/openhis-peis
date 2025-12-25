package com.center.medical.common.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 随行支付参数
 */
@Data
public class SuiXingConfig implements Serializable {
    private static final long serialVersionUID = 3045193062157303771L;

    @ApiModelProperty(value = "私钥")
    private String privateKey;

    @ApiModelProperty(value = "机构编号")
    private String orgId;

    @ApiModelProperty(value = "商户编号")
    private String mno;

}
