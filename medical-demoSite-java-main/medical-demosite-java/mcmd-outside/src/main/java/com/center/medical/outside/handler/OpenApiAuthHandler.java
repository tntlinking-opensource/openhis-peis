package com.center.medical.outside.handler;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.aes.AESUtils;
import com.center.medical.common.utils.rsa.RSAUtil;
import com.center.medical.outside.bean.dto.AESEncryptDto;
import com.center.medical.system.bean.model.CodeConfig;
import com.center.medical.system.service.CodeConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Objects;

/**
 * @author: 路飞
 * @date: 2023-02-07 14:33
 * @description: 对外开放接口的权限认证处理器
 */
@Slf4j
@Component
public class OpenApiAuthHandler implements HandlerInterceptor {
    @Resource
    private CodeConfigService codeConfigService;
    @Value("${open.aes.skey}")
    private String skey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 获取原始请求参数
        String data = request.getParameter("data");
        //log.info("1、原始请求参数：{}", data);
        if (StringUtils.isBlank(data)) {
            //log.info("2、请求参数为空！");
            errorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "请求参数不合法！");
            return false;
        }
        AESEncryptDto aesEncryptDto = null;
        try {
            //AES解密
            String decrypt = AESUtils.Decrypt(data, skey);
            aesEncryptDto = JSONUtil.toBean(decrypt, AESEncryptDto.class);
            //log.info("3、解密及数据清洗AES解密：{}", decrypt, aesEncryptDto);
        } catch (Exception e) {
            //log.info("4、AES解密失败！");
            errorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "请求参数不合法！");
            return false;
        }

        if (Objects.isNull(aesEncryptDto) || StringUtils.isBlank(aesEncryptDto.getEncryptData())) {
            //log.info("5、AES解密后参数为空！");
            errorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "请求参数不合法！");
            return false;
        }

        //获取授权码
        String authCode = aesEncryptDto.getAuthCode();
        String flag = aesEncryptDto.getFlag();
        if (StringUtils.isBlank(authCode) || StringUtils.isBlank(flag)) {
            //log.info("6、授权码或者标识码为空！");
            errorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "请求参数不合法！");
            return false;
        }

        //验证授权码是否有效
        CodeConfig authCodeItem = codeConfigService.getOne(new LambdaQueryWrapper<CodeConfig>().eq(CodeConfig::getAuthCode, authCode)
                .eq(CodeConfig::getBsFlag, flag).eq(CodeConfig::getStatus, 1).gt(CodeConfig::getExpiryDate, new Date()));
        if (Objects.isNull(authCodeItem)) {
            //log.info("7、授权码查询结果为空！");
            errorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "授权已过期！");
            return false;
        }

        // RSA解密
        //log.info("8、解密及数据清洗rsaConfig：{}", JSONUtil.toJsonStr(authCodeItem));
        String decryptData = RSAUtil.privateDecrypt(aesEncryptDto.getEncryptData(), authCodeItem.getValueText());
        //log.info("9、解密及数据清洗，请求参数decryptData：{}", decryptData);

        if (decryptData.equals(authCode)) {
            // 将新参数添加到请求中
            request.setAttribute("dataStr", null);
            //log.info("接口参数1：{}", request.getParameter("data"));
        } else {
            request.setAttribute("dataStr", JSONUtil.toJsonStr(StringUtils.substringAfter(decryptData, authCode)));
            //log.info("接口参数2：{}", request.getAttribute("dataStr"));
        }
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
        R<Object> fail = R.fail(code, msg);
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
