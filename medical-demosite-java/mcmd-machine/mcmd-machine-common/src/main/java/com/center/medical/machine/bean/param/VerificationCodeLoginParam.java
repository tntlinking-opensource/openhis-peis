package com.center.medical.machine.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 验证码登录参数
 */
@Data
public class VerificationCodeLoginParam implements Serializable {

    @NotBlank(message = "请填写身份证号!")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @NotBlank(message = "请填写手机号!")
    @ApiModelProperty(value = "手机号")
    private String phone;

    @NotBlank(message = "请填写验证码!")
    @ApiModelProperty(value = "验证码")
    private String code;

}
