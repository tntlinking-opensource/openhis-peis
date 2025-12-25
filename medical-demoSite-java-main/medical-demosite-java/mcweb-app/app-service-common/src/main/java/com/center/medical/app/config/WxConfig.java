package com.center.medical.app.config;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.core.util.StrUtil;
import com.center.medical.app.common.bean.WxApp;
import com.center.medical.app.common.bean.WxMiniApp;
import com.center.medical.app.common.bean.WxMp;
import com.center.medical.app.common.bean.WxPay;
import com.center.medical.app.common.enums.PayType;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.EntPayService;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.EntPayServiceImpl;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 通过微信配置获取微信的支付信息，登陆信息等
 *
 * @author LGH
 */
@Component
@AllArgsConstructor
public class WxConfig {

    private final ShopConfig shopConfig;

    private final RedissonClient redissonClient;

    /**
     * 根据支付方式，获取微信支付信息
     *
     * @param payType
     * @return
     */
    public WxPayService getWxPayService(PayType payType) {

        String appid;
        // 小程序支付
        if (Objects.equals(payType, PayType.WECHATPAY)) {
            WxMiniApp wxMiniApp = shopConfig.getWxMiniApp();
            appid = wxMiniApp.getAppId();
        } else if (Objects.equals(payType, PayType.WECHATPAY_APP)) {
            WxApp wxApp = shopConfig.getWxApp();
            appid = wxApp.getAppId();
        } else {
            WxMp wxMp = shopConfig.getWxMp();
            appid = wxMp.getAppId();
        }

        WxPayConfig payConfig = getWxPay(appid);
        payConfig.setSignType(WxPayConstants.SignType.MD5);

        WxPayService wxPayService = new WxPayServiceImpl();

        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

    public WxMaService getWxMaService() {
        WxMiniApp wxMiniApp = shopConfig.getWxMiniApp();
        WxMaInRedisConfig wxMaInRedisConfig = new WxMaInRedisConfig(wxMiniApp, redissonClient);

        WxMaServiceClusterImpl service = new WxMaServiceClusterImpl();
        service.setWxMaConfig(wxMaInRedisConfig);
        service.setRedissonClient(redissonClient);
        return service;
    }


    public WxMpService getWxMpService() {
        WxMp wxMp = shopConfig.getWxMp();
        WxMpInRedisConfigStorage wxMaInRedisConfig = new WxMpInRedisConfigStorage(wxMp);

        WxMpServiceClusterImpl service = new WxMpServiceClusterImpl();
        service.setWxMpConfigStorage(wxMaInRedisConfig);
        service.setRedissonClient(redissonClient);
        return service;
    }


    public EntPayService getEntPayService() {
        String appid;
        WxMiniApp wxMiniApp = shopConfig.getWxMiniApp();
        // 小程序支付
        if (wxMiniApp != null && StrUtil.isNotBlank(wxMiniApp.getAppId())) {

            appid = wxMiniApp.getAppId();
        } else {
            WxMp wxMp = shopConfig.getWxMp();
            appid = wxMp.getAppId();
        }

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(getWxPay(appid));
        return new EntPayServiceImpl(wxPayService);
    }

    private WxPayConfig getWxPay(String appid) {
        WxPay wxPay = shopConfig.getWxPay();
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(appid);
        payConfig.setKeyPath(wxPay.getKeyPath());
//        payConfig.setMchId(wxPay.getMchId());
//        payConfig.setMchKey(wxPay.getMchKey());
        payConfig.setMchId("1603458846");//暂时用麦沃德的
        payConfig.setMchKey("cQTdRpo0ejwVeU9F6RCfqOGBuv6XEyJq");
        payConfig.setApiV3Key("cQTdRpo0ejwVeU9F6RCfqOGBuv6XEyJq");
        String local = System.getProperty("user.dir") + "/certificate/";
        payConfig.setPrivateKeyPath(local + "apiclient_key.pem");
        payConfig.setPrivateCertPath(local + "apiclient_cert.pem");
        return payConfig;
    }
}
