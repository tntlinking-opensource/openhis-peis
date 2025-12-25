/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.security.common.even;

import com.center.medical.app.security.common.bo.UserInfoInTokenBO;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * 签名访问事件
 *
 * @author LHD
 */
@Data
@AllArgsConstructor
public class SysSignEvent {

    /**
     * 积分日志类型
     */
    private UserInfoInTokenBO userInfoInTokenBO;

    /**
     * 积分数额
     */
    private HttpServletRequest req;

    /**
     * 积分数额
     */
    private String accessId;


}
