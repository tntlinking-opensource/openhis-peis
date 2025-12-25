package com.center.medical.app.security.common.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.center.medical.app.common.enums.AppHttpStatus;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.handler.HttpHandler;
import com.center.medical.app.common.util.Json;
import com.center.medical.app.common.wrapper.ResponseWrapper;
import com.center.medical.app.security.common.adapter.AuthConfigAdapter;
import com.center.medical.app.security.common.adapter.SignAuthAdapter;
import com.center.medical.app.security.common.bo.UserInfoInTokenBO;
import com.center.medical.app.security.common.manager.TokenStore;
import com.center.medical.app.security.common.util.AuthUserContext;
import com.center.medical.app.security.common.util.SignUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 授权过滤，只要实现AuthConfigAdapter接口，添加对应路径即可：
 *
 * @author FrozenWatermelon
 * @date 2020/7/11
 */
@Component
public class AuthFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    private static final String SIGN_PRE = "sign-";

    @Autowired
    private AuthConfigAdapter authConfigAdapter;

    @Autowired
    private HttpHandler httpHandler;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private SignAuthAdapter signAuthAdapter;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String requestUri = req.getRequestURI();

        List<String> excludePathPatterns = authConfigAdapter.excludePathPatterns();

        AntPathMatcher pathMatcher = new AntPathMatcher();
        // 如果匹配不需要授权的路径，就不需要校验是否需要授权
        if (CollectionUtil.isNotEmpty(excludePathPatterns)) {
            for (String excludePathPattern : excludePathPatterns) {
                if (pathMatcher.match(excludePathPattern, requestUri)) {
                    chain.doFilter(req, resp);
                    return;
                }
            }
        }

        String accessToken = req.getHeader("Authorization");
        String accessId = req.getHeader(SignUtils.ACCESS_ID);
        // 也许需要登录，不登陆也能用的uri
        // 比如优惠券接口，登录的时候可以判断是否已经领取过
        // 不能登录的时候会看所有的优惠券，等待领取的时候再登录
        boolean mayAuth = pathMatcher.match(AuthConfigAdapter.MAYBE_AUTH_URI, requestUri);


        UserInfoInTokenBO userInfoInToken = null;

        try {
            // 通过签名访问
            if (StrUtil.isNotBlank(accessId)) {
                userInfoInToken = signAuthAdapter.loadUserInfoInToken(req, accessId);
                if (Objects.isNull(userInfoInToken)) {
                    throw new AppBindException(AppHttpStatus.UNAUTHORIZED, "Access-Id is error");
                }
            }
            // 如果有token，就要获取token
            else if (StrUtil.isNotBlank(accessToken)) {

                // token访问					// 这个方法不是controller里面的方法，所以抛出来的异常不会被捕获
                userInfoInToken = tokenStore.getUserInfoByAccessToken(accessToken, true);
            } else if (!mayAuth) {
                // 返回前端401
                httpHandler.printServerResponseToWeb(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.value());
                return;
            }

            // 保存上下文
            AuthUserContext.set(userInfoInToken);

            // 非签名访问
            if (StrUtil.isBlank(accessId)) {
                chain.doFilter(req, resp);
            }
            // 签名访问
            else {
                ResponseWrapper responseWrapper = new ResponseWrapper(resp);
                chain.doFilter(req, responseWrapper);
                byte[] content = responseWrapper.getContent();
                // 获取相应数据
                String data = null;
                if (content.length > 0) {
                    data = new String(content, StandardCharsets.UTF_8);
                }
                // 数据加签
                String key = signAuthAdapter.loadSign(accessId);
                String signTime = String.valueOf(System.currentTimeMillis());
                TreeMap<String, String> map = new TreeMap<>();
                map.put(SignUtils.ACCESS_TIME, signTime);
                map.put(SignUtils.ACCESS_ID, accessId);
                map.put(SignUtils.ACCESS_DATA, data);
                responseWrapper.setHeader(SignUtils.ACCESS_TIME, signTime);
                String sign = SignUtils.sign(Json.toJsonString(map), key);
                responseWrapper.setHeader(SignUtils.ACCESS_SIGN, sign);
                if (Objects.nonNull(data)) {
                    ServletOutputStream outputStream = resp.getOutputStream();
                    outputStream.write(data.getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                }
            }
        } catch (Exception e) {
            // 手动捕获下非controller异常
            if (e instanceof AppBindException) {
                httpHandler.printServerResponseToWeb(e.getMessage(), ((AppBindException) e).getHttpStatusCode());
            } else {
                throw e;
            }
        } finally {
            AuthUserContext.clean();
        }

    }
}
