package com.center.medical.enterprise.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 随行支付
 */
@Data
public class SuiXingPay implements Serializable {

    private static final long serialVersionUID = 7986864732993800110L;

    @ApiModelProperty(value = "私钥")
    private String privateKey;

    @ApiModelProperty(value = "机构编号")
    private String orgId;

    @ApiModelProperty(value = "商户编号")
    private String mno;
}
