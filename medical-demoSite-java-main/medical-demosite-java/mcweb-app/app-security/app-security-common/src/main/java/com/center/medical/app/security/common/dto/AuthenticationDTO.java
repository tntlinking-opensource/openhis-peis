package com.center.medical.app.security.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用于登陆传递账号密码
 *
 * @author FrozenWatermelon
 * @date 2020/7/1
 */
@Data
public class AuthenticationDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "principal不能为空")
    @ApiModelProperty(value = "用户名/邮箱/手机号", required = true)
    protected String principal;

    /**
     * 密码
     */
    @NotBlank(message = "credentials不能为空")
    @ApiModelProperty(value = "一般用作密码", required = true)
    protected String credentials;

}
