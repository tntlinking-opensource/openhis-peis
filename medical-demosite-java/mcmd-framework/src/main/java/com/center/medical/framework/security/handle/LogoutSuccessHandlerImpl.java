package com.center.medical.framework.security.handle;

import com.alibaba.fastjson.JSON;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.HttpStatus;
import com.center.medical.common.core.domain.AjaxResult;
import com.center.medical.common.core.domain.model.LoginUser;
import com.center.medical.common.utils.ServletUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.framework.manager.AsyncManager;
import com.center.medical.framework.manager.factory.AsyncFactory;
import com.center.medical.framework.web.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 *
 * @author 路飞
 */
@Slf4j
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Resource
    private TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);
//        log.info("LogoutSuccessHandlerImpl.onLogoutSuccess.loginUser：{}", JSONUtil.toJsonStr(loginUser));
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "退出成功"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(HttpStatus.SUCCESS, "退出成功")));
    }
}
