package com.center.medical.center.qingdao.profession.utils;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.log.Log;
import com.center.medical.center.qingdao.profession.entity.dto.HnResult;
import com.center.medical.center.qingdao.profession.entity.dto.Result;
import com.center.medical.center.qingdao.profession.exception.ErrorRuntimeException;
import com.ejlchina.okhttps.HTTP;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;

@Service
@Transactional
@Slf4j
public class RequestService {
    private final HTTP http;
    private final ObjectMapper objectMapper;

    public RequestService(HTTP http, ObjectMapper objectMapper) {
        this.http = http;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public Result requestPost(String url, Map<String, String> params) throws IORuntimeException {
        try (CloseableHttpClient httpClient = sslClient()) {
            HttpPost post = new HttpPost(url);
            ArrayList<NameValuePair> parameters = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, StandardCharsets.UTF_8);
            post.setEntity(entity);
            log.info("post.getURI() = " + post.getURI());
            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String string = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
            log.info("string = " + string);
            return objectMapper.readValue(string, Result.class);
        } catch (IOException e) {
            log.info("其他错误",e);
            return new Result();
        }
    }

    @SneakyThrows
    public Result requestPost(String url, Map<String, String> params, Map<String, String> headers) throws IORuntimeException {
        try (CloseableHttpClient httpClient = sslClient()) {
            HttpPost post = new HttpPost(url);
            ArrayList<NameValuePair> parameters = new ArrayList<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, StandardCharsets.UTF_8);
            post.setEntity(entity);
            for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {

                post.setHeader(stringStringEntry.getKey(), stringStringEntry.getValue());
            }
            log.info("post.getURI() = " + post.getURI());
            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String string = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
            log.info("string = " + string);
            return objectMapper.readValue(string, Result.class);
        } catch (IOException e) {
            log.info("其他错误",e);
            return new Result();
        }
    }



    @SneakyThrows
    public Result post(String url, Map<String, String> params, Map<String, String> headers) {
        String string = http.sync(url).addBodyPara(params).addHeader(headers).post().getBody().toString();
        try{
            return objectMapper.readValue(string, Result.class);
        }catch(Exception e){
            throw new ErrorRuntimeException(string);
        }
    }

    @SuppressWarnings("deprecation")
    public Result requestPostJson(String url, Object params) {
        try (CloseableHttpClient httpClient = sslClient()) {
            SpringUtil.getBean(ObjectMapper.class);
            HttpPost post = new HttpPost(url);
            StringEntity entity = new StringEntity(objectMapper.writeValueAsString(params), StandardCharsets.UTF_8);
            entity.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            post.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String string = EntityUtils.toString(httpEntity, StandardCharsets.UTF_8);
            return objectMapper.readValue(string, Result.class);
        } catch (IOException e) {
//            throw new IORuntimeException(e);
            log.info("其他错误",e);
            return new Result();
        }
    }

    private CloseableHttpClient sslClient() {
        try {
            // 在调用SSL之前需要重写验证方法，取消检测SSL
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                }
            };
            SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            ctx.init(null, new TrustManager[]{trustManager}, null);
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            // 创建Registry
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                    .setExpectContinueEnabled(Boolean.TRUE).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).build();
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", socketFactory).build();
            // 创建ConnectionManager，添加Connection配置信息
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(requestConfig).build();
            return closeableHttpClient;
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }


    @SneakyThrows
    public Result post(String url, HashMap<String, String> params) {
        String string = http.sync(url).addBodyPara(params).post().getBody().toString();
        return objectMapper.readValue(string, Result.class);
    }


    @SneakyThrows
    public String doPostForm(String url, HashMap<String, String> map,String token){
        map.put("token", token);
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        map.forEach((key, value) -> builder.addFormDataPart(key, value));

        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .header("Content-Type", "multipart/form-data")
                .build();
        // 发送请求
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            log.info("获取token失败");
            throw new RuntimeException(e);
        } finally {
            if (response != null) {
                response.close(); // 确保关闭响应
            }
        }
    }



    @SneakyThrows
    public HnResult doHnPost(String url, HashMap<String, String> params) {
        String string = http.sync(url).addBodyPara(params).post().getBody().toString();
        return objectMapper.readValue(string, HnResult.class);
    }

}
