package com.center.medical.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 生成二维码 返回数据
 */
@Data
public class GenerateQRCodeDto implements Serializable {
    private static final long serialVersionUID = 7020447421931297772L;

    @ApiModelProperty(value = "支付串")
    private String payinfo;

}
