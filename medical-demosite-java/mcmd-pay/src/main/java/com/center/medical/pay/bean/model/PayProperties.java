package com.center.medical.pay.bean.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: mecreg
 * @description:
 * @author: yuane
 * @create: 2020-07-08 08:38
 */
@Component
public class PayProperties {
    @Value("${pay.proxyUrl}")
    private String proxyUrl;
    @Value("${pay.proxyRegisterUrl}")
    private String proxyRegisterUrl;
    @Value("${pay.proxyOtherUrl}")
    private String proxyOtherUrl;
    @Value("${pay.proxyAddUrl}")
    private String proxyAddUrl;

    @Value("${pay.appid}")
    private String appid;
    @Value("${pay.c}")
    private String c;
    @Value("${pay.key}")
    private String key;
    @Value("${pay.websocket}")
    private String websocket;
    @Value("${kangtao.post.url}")
    private String kangtaoPostUrl;
    @Value("${kangtao.post.enabled}")
    private String kangtaoPostEnable = "false";

    public String getProxyUrl() {
        return proxyUrl;
    }

    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getKey() {
        return key;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public String getWebsocket() {
        return websocket;
    }

    public void setWebsocket(String websocket) {
        this.websocket = websocket;
    }

    public String getProxyRegisterUrl() {
        return proxyRegisterUrl;
    }

    public void setProxyRegisterUrl(String proxyRegisterUrl) {
        this.proxyRegisterUrl = proxyRegisterUrl;
    }

    public String getProxyOtherUrl() {
        return proxyOtherUrl;
    }

    public void setProxyOtherUrl(String proxyOtherUrl) {
        this.proxyOtherUrl = proxyOtherUrl;
    }

    public String getProxyAddUrl() {
        return proxyAddUrl;
    }

    public void setProxyAddUrl(String proxyAddUrl) {
        this.proxyAddUrl = proxyAddUrl;
    }

    public String getKangtaoPostUrl() {
        return kangtaoPostUrl;
    }

    public void setKangtaoPostUrl(String kangtaoPostUrl) {
        this.kangtaoPostUrl = kangtaoPostUrl;
    }

    public String getKangtaoPostEnable() {
        return kangtaoPostEnable;
    }

    public void setKangtaoPostEnable(String kangtaoPostEnable) {
        this.kangtaoPostEnable = kangtaoPostEnable;
    }
}