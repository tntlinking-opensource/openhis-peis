/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yami
 */
@Data
@ApiModel(value = "设置用户信息")
public class UserPwdUpdateParam {

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "校验登陆注册验证码成功的标识")
    private String checkRegisterSmsFlag;


}
