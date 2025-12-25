package com.center.medical.center.qingdao.profession.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.center.medical.center.qingdao.profession.cache.JZTokenCache;
import com.center.medical.center.qingdao.profession.cache.TokenCache;
import com.center.medical.center.qingdao.profession.entity.dto.Result;
import com.center.medical.center.qingdao.profession.entity.properties.ConfigProperties;
import com.center.medical.center.qingdao.profession.entity.properties.JzjkProperties;
import com.center.medical.center.qingdao.profession.service.LoginService;
import com.center.medical.center.qingdao.profession.utils.RequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import static com.alibaba.druid.sql.ast.TDDLHint.Type.JSON;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final ConfigProperties properties;
    private final RequestService requestService;
    private final TokenCache cache;
    private final JZTokenCache jzCache;
    private final JzjkProperties jzProperties;

    @Override
    public String getToken(String userCode, String password) {
        TokenCache.TokenInfo token = cache.getToken();
        if (token == null || (ObjectUtil.isEmpty(token.getToken()) || Math.abs(DateUtil.between(token.getExpiration(), new Date(), DateUnit.HOUR)) >= 23)) {
            Result result = getResult(userCode, password);
            cache.setToken(new TokenCache.TokenInfo(result.getToken()));
        }
        return cache.getToken().getToken();
    }

    private Result getResult(String userCode, String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("usercode", userCode);
        params.put("password", password);
        Result result = requestService.post(properties.getRequestUrl() + "/index/Token/login", params);
        log.info("result.getToken() : " + result.getToken());
        /**
         * 账号密码可能不对
         * 直接在浏览器中尝试：https://222.173.107.23:1589/index/Token/login?usercode=37020311&password=hdzkzy001
         * 以前会导致报错：
         * java.lang.NullPointerException: token is marked non-null but is null
         * Caused by: org.springframework.transaction.UnexpectedRollbackException: Transaction silently rolled back because it has been marked as rollback-only
         * 这里应使用 平台编号+国家平台接口密码
         * 而不是使用登录密码
         */
        if(StrUtil.isEmpty(result.getToken())){
            log.info("获取token失败", JSONUtil.toJsonStr(result));
            throw new RuntimeException("获取token失败,"+JSONUtil.toJsonStr(result));
        }
        return result;
    }

    @Override
    public String getToken() {
        return getToken(properties.getUserCode(), properties.getPassword());
    }

    @Override
    public void refreshToken() {
        Result result = getResult(properties.getUserCode(), properties.getPassword());
        cache.setToken(new TokenCache.TokenInfo(result.getToken()));
    }


    @Override
    public String getJzToken() {
        JZTokenCache.TokenInfo token = jzCache.getToken();
        if (token == null || (ObjectUtil.isEmpty(token.getToken()) || Math.abs(DateUtil.between(token.getExpiration(), new Date(), DateUnit.HOUR)) >= 23)) {
            String url = jzProperties.getUrl() + "/index/Token/login";
            OkHttpClient client = new OkHttpClient();
            // 创建 multipart 请求体
            MultipartBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("usercode", jzProperties.getUserCode())
                    .addFormDataPart("password", jzProperties.getPassword())
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            // 发送请求
            Response response = null;
            try {
                response = client.newCall(request).execute();
                ObjectMapper objectMapper = new ObjectMapper();
                Result result = objectMapper.readValue(response.body().string(), Result.class);
                log.info("胶州token是：{}",result.getToken());
                jzCache.setToken(new JZTokenCache.TokenInfo(result.getToken()));
            } catch (Exception e) {
                log.info("获取token失败");
                throw new RuntimeException(e);
            } finally {
                if (response != null) {
                    response.close(); // 确保关闭响应
                }
            }
        }
        return jzCache.getToken().getToken();
    }
}
