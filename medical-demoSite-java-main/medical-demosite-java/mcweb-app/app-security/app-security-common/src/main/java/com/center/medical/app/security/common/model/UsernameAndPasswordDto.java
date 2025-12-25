package com.center.medical.app.security.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author yami
 */
@Data
@ApiModel(value = "用户名和密码参数")
public class UsernameAndPasswordDto {

    @NotBlank(message = "用户名不能为空")
    @Size(max = 50)
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(max = 50)
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @NotBlank(message = "验证码不能为空")
    @Size(max = 50)
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
}
