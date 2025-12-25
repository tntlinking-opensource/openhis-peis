/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.security.common.adapter;

import com.center.medical.app.security.common.bo.UserInfoInTokenBO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


/**
 * @author lgh on 2018/09/07.
 */
@Service
@AllArgsConstructor
public class ApiSignAuthAdapter implements SignAuthAdapter {

    @Override
    public UserInfoInTokenBO loadUserInfoInToken(HttpServletRequest req, String accessId) {
        return null;
    }

    @Override
    public String loadSign(String accessId) {
        return null;
    }
}
