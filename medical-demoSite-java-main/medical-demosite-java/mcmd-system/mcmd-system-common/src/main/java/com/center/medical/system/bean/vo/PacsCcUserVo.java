package com.center.medical.system.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 彩超登录获取所有有权限的医生 返回数据
 */
@Data
public class PacsCcUserVo implements Serializable {
    private static final long serialVersionUID = 4506740394559191945L;

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "输入码")
    private String inputCode;

    @ApiModelProperty(value = "密码")
    private String password;

}
