/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yami
 */
@Data
public class UserDto {

    @ApiModelProperty(value = "用户状态：0禁用 1正常", required = true)
    private Integer status;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "性别：0.男 1.女")
    private Integer sex;

    @ApiModelProperty(value = "用户手机号")
    private String userMobile;

    @ApiModelProperty(value = "用户手机号隐藏位数")
    private String mobile;

    @ApiModelProperty(value = "头像图片路径")
    private String pic;

    @ApiModelProperty(value = "用户生日")
    private String birthDate;
    /**
     * 用户等级
     */
    @ApiModelProperty(value = "用户等级")
    private Integer level;
    /**
     * 等级条件 0 普通会员 1 付费会员
     */
    @ApiModelProperty(value = "等级条件 0 普通会员 1 付费会员")
    private Integer levelType;

    @ApiModelProperty(value = "家庭管理人数")
    private long familyListSize;
}
