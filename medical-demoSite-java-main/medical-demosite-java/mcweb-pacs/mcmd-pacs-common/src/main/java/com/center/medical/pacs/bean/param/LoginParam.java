package com.center.medical.pacs.bean.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登陆参数
 */
@Data
public class LoginParam implements Serializable {
    private static final long serialVersionUID = -128322980295649031L;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("密码(必填)")
    private String password;
}
