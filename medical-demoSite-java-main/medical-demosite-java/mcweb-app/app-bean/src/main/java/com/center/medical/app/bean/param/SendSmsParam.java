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

import com.center.medical.app.common.util.PrincipalUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @author Yami
 */
@Data
public class SendSmsParam {

    @ApiModelProperty(value = "手机号")
    @Pattern(regexp = PrincipalUtil.MOBILE_REGEXP, message = "请输入正确的手机号")
    private String mobile;

    @ApiModelProperty(value = "是否店铺账号，1是")
    private Integer shopAccount;

}
