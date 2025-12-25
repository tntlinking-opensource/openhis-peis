package com.center.medical.outside.handler;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.rsa.RSAUtil;
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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Objects;

/**
 * @author: 路飞
 * @date: 2023-02-07 14:33
 * @description: 对外开放接口的权限认证处理器
 */
@Slf4j
@Component
public class OpenApiAuthV2Handler implements HandlerInterceptor {
    @Resource
    private CodeConfigService codeConfigService;
    @Value("${open.aes.skey}")
    private String skey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 获取原始请求参数
        String data = URLDecoder.decode(request.getParameter("data"));
        //获取授权码
        String authCode = request.getParameter("code");
        //业务标识
        String flag = request.getParameter("flag");
        log.info("1、原始请求参数：{}、{}、{}", authCode, flag, data);
        if (StringUtils.isBlank(data)) {
            log.warn("2、请求参数为空！");
            errorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "请求参数不合法！");
            return false;
        }

        if (StringUtils.isBlank(authCode) || StringUtils.isBlank(flag)) {
            log.warn("3、授权码或者标识码为空！");
            errorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "请求参数不合法！");
            return false;
        }

        //验证授权码是否有效
        CodeConfig authCodeItem = codeConfigService.getOne(new LambdaQueryWrapper<CodeConfig>().eq(CodeConfig::getAuthCode, authCode)
                .eq(CodeConfig::getBsFlag, flag).eq(CodeConfig::getStatus, 1).gt(CodeConfig::getExpiryDate, new Date()));
        if (Objects.isNull(authCodeItem)) {
            log.warn("4、授权码查询结果为空！");
            errorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "授权已过期！");
            return false;
        }

        // RSA解密
        log.info("8、解密及数据清洗rsaConfig：{}", JSONUtil.toJsonStr(authCodeItem));
        String decryptData = RSAUtil.privateDecrypt(data, authCodeItem.getValueText());
        log.info("9、解密及数据清洗，请求参数decryptData：{}", decryptData);

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

    public static void main(String[] args) {
        System.out.println(URLEncoder.encode("i0s213wJQoJ91FYY8qFk1y9SUcXHTzWcyvgfO0RL90ACOjg0NOK3pQDVbMdLFp53/uKg9GdYfDd0yJJc8Yet6Pz8HHm14UTFhrJbkXKKJKyRGwkZmKmHMbtuBhTDYeP+uCbXcb7O3bxj7O9o1Pyr9jvLNLTQ5RDN0ov23QErqehdZxd78U4Vak/6UOH/tYZqP5oN1mX7glqo64EOFL9coJbaFeVjR+rdLXiSqDQf1JLKUXlEZMMq9sjBUF2UP2tWHyNWFJiZEmDtqkHClN4mIl5B1P6KE6A98oBWuaxgqX5JQWuFyLlPMdePoCVdru2FsJ4ipdpKbYz/ClAE+3yHsV8ycBmI6DLF0c2f8CW+YhMIm7SWFf88em0Tx6JKNt6NmQ9yp3hgA81zfmiAeVIEN9aITLuMyH1oKFK+6EnLMD+UmdBlVMffE6IcgDBAnPXG+0n6O5z7P4jA9p82JqknPHElEbCSE+YGrT+ZhdeyyZyR99iEFtGIsL+imHCHnBY/g5Y6VUN8h8OwWr5sJUurW8a4XmtHmQPmCM6+uFPnNPab28YV6HIWkdcexn30PSq13XKKTUvGtbQnbGRHR1X/z7v9VmRJdCwfe3ehSu3LW3QmSrnZjt3gUob+N8SgQtWjjmpwCxQtMldmqnHD7VemMiS0Lv+UDcUQOkX7Cnutv70UMM55v4lPr/88WcZ0nEdMzf4vBIB3mRKDlV5/z4sGKZX7otBFgts+Ndr5I7Bl8uSXQh7Owz81Zygb2d4mEIshKstWHim2wzeawNf4km23PyydKQd92NOkYyRN9YvAQ+OTapZbLnx4+VL4u5MiJJiYbvvRzNQQdqs0LXOdEK/COA=="));
    }


}
