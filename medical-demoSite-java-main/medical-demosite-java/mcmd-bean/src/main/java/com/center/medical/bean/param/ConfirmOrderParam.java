package com.center.medical.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 平安好医生 到检确认 参数
 */
@Data
public class ConfirmOrderParam implements Serializable {
    private static final long serialVersionUID = 629512938516198995L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "验证码")
    private String captcha;
}
