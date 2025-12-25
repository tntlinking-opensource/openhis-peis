package com.center.medical.app.security.common.adapter;

import com.center.medical.app.security.common.bo.UserInfoInTokenBO;

import javax.servlet.http.HttpServletRequest;

/**
 * 实现该接口之后，修改需要授权登陆的路径，不需要授权登陆的路径
 *
 * @author FrozenWatermelon
 * @date 2020/7/4
 */
public interface SignAuthAdapter {

    /**
     * 用户信息
     *
     * @param req
     * @param accessId
     * @return
     */
    UserInfoInTokenBO loadUserInfoInToken(HttpServletRequest req, String accessId);

    /**
     * 获取密钥
     *
     * @param accessId
     * @return
     */
    String loadSign(String accessId);

}
