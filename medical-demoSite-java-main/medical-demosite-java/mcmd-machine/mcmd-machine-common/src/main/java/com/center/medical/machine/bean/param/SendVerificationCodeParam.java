package com.center.medical.machine.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 发送验证码参数
 */
@Data
public class SendVerificationCodeParam implements Serializable {

    @NotBlank(message = "请填写身份证号!")
    @ApiModelProperty(value = "身份证号")
    private String idcardno;

    @NotBlank(message = "请填写手机号!")
    @ApiModelProperty(value = "手机号")
    private String phone;

}
