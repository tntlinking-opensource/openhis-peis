package com.center.medical.app.security.util;

import com.center.medical.app.common.util.HttpContextUtils;
import com.center.medical.app.security.common.bo.UserInfoInTokenBO;
import com.center.medical.app.security.common.util.AuthUserContext;
import com.center.medical.app.security.model.YamiUser;
import lombok.experimental.UtilityClass;

/**
 * @author LGH
 */
@UtilityClass
public class SecurityUtils {

    private static final String USER_REQUEST = "/api/v1/";

    /**
     * 获取用户
     */
    public YamiUser getUser() {
        if (!HttpContextUtils.getHttpServletRequest().getRequestURI().startsWith(USER_REQUEST)) {
            // 用户相关的请求，应该以/p开头！！！
            throw new RuntimeException("用户相关的请求，应该以/api/v1开头！！！");
        }
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();

        YamiUser yamiUser = new YamiUser();
        yamiUser.setUserId(userInfoInTokenBO.getUserId());
        yamiUser.setBizUserId(userInfoInTokenBO.getBizUserId());
        yamiUser.setEnabled(userInfoInTokenBO.getEnabled());
        yamiUser.setShopId(userInfoInTokenBO.getShopId());
        yamiUser.setStationId(userInfoInTokenBO.getOtherId());
        return yamiUser;
    }
}
