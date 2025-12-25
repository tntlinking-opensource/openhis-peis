package com.center.medical.outside.handler;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.dto.CooCustomerInfo;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BsFlag;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.common.utils.rsa.RSAUtil;
import com.center.medical.outside.config.Constants;
import com.center.medical.system.bean.model.CodeConfig;
import com.center.medical.system.service.CodeConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author: 路飞
 * @date: 2023-02-07 14:33
 * @description: 对外开放接口的权限认证处理器：验证授权码是否有效，有效则开始对data参数进行RSA非对称解密，解密成功后将请求参数加入request中，最后验证是否Authorization，如果是登录则跳过，否则验证token是否有效，有效则放行
 *
 */
@Slf4j
@Component
public class OpenApiAuthV3Handler implements HandlerInterceptor {
    @Resource
    private CodeConfigService codeConfigService;

//    public static void main(String[] args) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("code", "80C8A8EFFC1E8552");
//        params.put("flag", "COO_CUSTOMER");
//        params.put("data", "bzSGhUth9g2uPwn9aDxHO0GHWqCIyaZPaPOX68DgHdwzHUlefViy2NCMgCN+YFnimy71C1hZr5j8vMJbCl5Ysxo+IiU9MrRZdeKJer+iuvXn5N3obc5WKBl8UOF5uMbZzLCGA5LbpUh/4RHiOWzc5zu4UR+Zmji44zhh1AoKMto=".replace("+", "%2B"));
////rCIctitFeOgd3/RFHulOY1DTG3PoEpUCG9OKHwufX5KV529JGLNDXr2I+or31sQi
//        // 明确指定编码为UTF-8
//        String post = HttpUtil.post("http://localhost:8081/open/api/v3/cooperate/customer", params);
//        System.out.println(post);
//    }

    public static void main(String[] args) {
        // 请求的URL
        String url = "http://localhost:8081/open/api/v3/cooperate/customer";

        // 创建HttpRequest对象，设置请求方法为POST
        HttpRequest request = HttpUtil.createPost(url);
        // 添加token到请求头部
        request.header("Authorization", "rCIctitFeOgd3/RFHulOY1DTG3PoEpUCG9OKHwufX5KV529JGLNDXr2I+or31sQi");
        Map<String, Object> params = new HashMap<>();
        params.put("code", "58DE541BA12DCC8B");
        params.put("flag", "COO_CUSTOMER");
        params.put("data", "bzSGhUth9g2uPwn9aDxHO0GHWqCIyaZPaPOX68DgHdwzHUlefViy2NCMgCN+YFnimy71C1hZr5j8vMJbCl5Ysxo+IiU9MrRZdeKJer+iuvXn5N3obc5WKBl8UOF5uMbZzLCGA5LbpUh/4RHiOWzc5zu4UR+Zmji44zhh1AoKMto=".replace("+", "%2B"));

        request.form(params);
        // 发送请求并获取响应
        HttpResponse response = request.execute();
        // 获取响应内容并输出（根据实际情况可能需要进一步处理响应，比如判断状态码等）
        String result = response.body();
        System.out.println(result);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 用于存储获取到的所有参数
        Map<String, String[]> parameterMap = new HashMap<>();

        // 获取请求中的所有参数名
        Enumeration<String> parameterNames = request.getParameterNames();
        log.error("parameterNames：{}", JSONUtil.toJsonStr(parameterNames));
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            // 根据参数名获取对应的值（可能是单个值也可能是多个值，所以是数组形式）
            String[] parameterValues = request.getParameterValues(parameterName);
            log.error("parameterValues：{}", parameterValues);
            parameterMap.put(parameterName, parameterValues);
        }

        // 获取原始请求参数
        String data = request.getParameter("data").replace("%2B", "+");
        log.info("1.0、原始请求参数：{}", data);
        //获取授权码
        String authCode = request.getParameter("code");
        //业务标识
        String flag = request.getParameter("flag");
        log.info("1.0、原始请求参数：{}、{}, {}", authCode, flag, data);

        if (StringUtils.isBlank(data)) {
            log.warn("1.1、请求参数为空！");
            errorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "请求参数不合法！");
            return false;
        }

        if (StringUtils.isBlank(authCode)) {
            log.warn("1.2、授权码不能为空，authCode={}", authCode);
            errorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "授权码不能为空！");
            return false;
        }
        if (StringUtils.isBlank(flag)) {
            log.warn("1.3、业务标识码不能为空，flag={}", flag);
            errorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "业务标识码不能为空！");
            return false;
        }

        //验证授权码是否有效
        CodeConfig authCodeItem = codeConfigService.getOne(new LambdaQueryWrapper<CodeConfig>().eq(CodeConfig::getAuthCode, authCode)
                .eq(CodeConfig::getBsFlag, flag).eq(CodeConfig::getStatus, 1).gt(CodeConfig::getExpiryDate, new Date()));
        if (Objects.isNull(authCodeItem)) {
            log.warn("2、授权码查询结果为空！");
            errorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "授权已过期！");
            return false;
        }

        // RSA解密
        log.info("3、解密及数据清洗rsaConfig：{}", JSONUtil.toJsonStr(authCodeItem));
        try {
            String decryptData = RSAUtil.privateDecrypt(data, authCodeItem.getValueText());
            log.info("4、解密及数据清洗，请求参数decryptData：{}", decryptData);
            if (decryptData.equals(authCode)) {
                // 将新参数添加到请求中
                request.setAttribute("dataStr", null);
                //log.info("接口参数1：{}", request.getParameter("data"));
            } else {
                request.setAttribute("dataStr", JSONUtil.toJsonStr(StringUtils.substringAfter(decryptData, authCode)));
                log.info("接口参数2：{}", request.getAttribute("dataStr"));
            }
        }catch (Exception e){
            log.error("5、解密失败！");
            errorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "参数解密失败！");
            return false;
        }

        CooCustomerInfo authInfo = new CooCustomerInfo();
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/open/api/v3/login")) {
            log.info("6、登录接口，无需授权：{}", requestURI);
        } else {
            // token
            String token = request.getHeader("Authorization");
            if (StringUtils.isNotBlank(token)) {
                // 在这里可以对获取到的authorizationHeader进行后续处理，比如验证等操作
                //判断授权是否有效
                authInfo = RedisUtil.get(BsFlag.COO_CUSTOMER + "_ACCESS_TOKEN:" + token);
                if (Objects.isNull(authInfo)){
                    log.warn("8、token已过期！");
                    errorResponse(response, HttpServletResponse.SC_FORBIDDEN, "token已过期！");
                    return false;
                }
                // 判断授权码是否是登录用户的授权码
                if (!authCodeItem.getSourceId().equals(authInfo.getSourceId())){
                    log.warn("9、授权码不是当前登录用户的授权码！");
                    errorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "授权码无效！");
                    return false;
                }
                // 刷新token的有效期
                RedisUtil.set(BsFlag.COO_CUSTOMER+ "_ACCESS_TOKEN:" + token, authInfo, Constants.ACCESS_TOKEN_EXPIRES_TIME);

            } else {
                log.warn("10、需授权接口，尚未登录！");
                errorResponse(response, HttpServletResponse.SC_FORBIDDEN, "尚未登录！");
                return false;
            }
        }

        authInfo.setBsFlag(authCodeItem.getBsFlag());
        authInfo.setAuthCode(authCode);
        authInfo.setCodeSetting(authCodeItem.getCodeSetting());
        authInfo.setSourceId(authCodeItem.getSourceId());
        request.setAttribute("authInfo", authInfo);
        log.warn("11、验证通过，放行！");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private void errorResponse(HttpServletResponse response, Integer code, String msg) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        R<Object> fail = R.fail(code, null, msg);
        String result = JSONUtil.toJsonStr(fail);
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.append(result);
    }



}
