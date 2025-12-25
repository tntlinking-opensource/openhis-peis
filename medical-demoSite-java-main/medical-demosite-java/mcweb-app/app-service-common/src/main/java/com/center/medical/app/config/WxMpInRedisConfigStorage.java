/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.config;

import com.center.medical.app.common.annotation.RedisLock;
import com.center.medical.app.common.bean.WxMp;
import com.center.medical.app.common.util.RedisUtil;
import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.enums.TicketType;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;

/**
 * 基于Redis的微信配置provider.
 * <p>
 * 已加入分布式锁的实现
 *
 * @author LGH
 */
public class WxMpInRedisConfigStorage extends WxMpDefaultConfigImpl {

    private static final String ACCESS_TOKEN_KEY = "wxMp:access_token:";

    private String accessTokenKey;

    public WxMpInRedisConfigStorage(WxMp wxMp) {
        this.setAppId(wxMp.getAppId());
        this.setSecret(wxMp.getSecret());
        this.setToken(wxMp.getToken());
        this.setAesKey(wxMp.getAesKey());
    }

    /**
     * 每个公众号生成独有的存储key.
     */
    @Override
    public void setAppId(String appId) {
        super.setAppId(appId);
        this.accessTokenKey = ACCESS_TOKEN_KEY.concat(appId);
    }

    private String getTicketRedisKey(TicketType type) {
        return String.format("wx:ticket:key:%s:%s", this.appId, type.getCode());
    }

    @Override
    public String getAccessToken() {
        return RedisUtil.get(accessTokenKey);
    }

    @Override
    public boolean isAccessTokenExpired() {
        return !RedisUtil.hasKey(accessTokenKey);
    }

    @Override
    @RedisLock(lockName = "updateMpAccessToken")
    public void updateAccessToken(WxAccessToken accessToken) {
        updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
    }


    @Override
    @RedisLock(lockName = "updateMpAccessToken")
    public void updateAccessToken(String accessToken, int expiresInSeconds) {
        RedisUtil.set(accessTokenKey, accessToken, expiresInSeconds - 200);
    }

    @Override
    public void expireAccessToken() {
        RedisUtil.del(accessTokenKey);
    }

    @Override
    public String getTicket(TicketType type) {
        return RedisUtil.get(this.getTicketRedisKey(type));
    }

    @Override
    public boolean isTicketExpired(TicketType type) {
        return !RedisUtil.hasKey(this.getTicketRedisKey(type));
    }

    @Override
    @RedisLock(lockName = "updateMpJsapiTicket")
    public void updateTicket(TicketType type, String jsapiTicket, int expiresInSeconds) {
        RedisUtil.set(this.getTicketRedisKey(type), jsapiTicket, expiresInSeconds - 200);
    }

    @Override
    public void expireTicket(TicketType type) {
        RedisUtil.del(this.getTicketRedisKey(type));
    }

}
