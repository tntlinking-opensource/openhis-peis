package com.center.medical.system.config;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.center.medical.bean.dto.WxApp;
import com.center.medical.bean.dto.WxMiniApp;
import com.center.medical.bean.dto.WxMp;
import com.center.medical.bean.dto.WxPay;
import com.center.medical.common.enums.PayType;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import lombok.AllArgsConstructor;
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

    private final SystemConfig systemConfig;

    /**
     * 根据支付方式，获取微信支付信息
     *
     * @param payType
     * @return
     */
    public WxPayService getWxPayService(PayType payType) {

        String appid;
        // 小程序支付
        if (Objects.equals(payType, PayType.WECHATPAY) || Objects.equals(payType, PayType.MIXPAY)) {
            WxMiniApp wxMiniApp = systemConfig.getWxMiniApp();
            appid = wxMiniApp.getAppId();
        } else if (Objects.equals(payType, PayType.WECHATPAY_APP)) {
            WxApp wxApp = systemConfig.getWxApp();
            appid = wxApp.getAppId();
        } else {
            WxMp wxMp = systemConfig.getWxMp();
            appid = wxMp.getAppId();
        }

        WxPayConfig payConfig = getWxPay(appid);
        payConfig.setSignType(WxPayConstants.SignType.MD5);

        WxPayService wxPayService = new WxPayServiceImpl();

        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

    private WxPayConfig getWxPay(String appid) {
        WxPay wxPay = systemConfig.getWxPay();
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(appid);
        payConfig.setMchId(wxPay.getMchId());
        payConfig.setMchKey(wxPay.getMchKey());
        payConfig.setKeyPath(wxPay.getKeyPath());
        return payConfig;
    }

    public WxMaService getWxMaService() {
        //调用工具包的服务
        WxMaService wxMaService = new WxMaServiceImpl();
        WxMaDefaultConfigImpl wxMaDefaultConfigImpl = new WxMaDefaultConfigImpl();
        WxMiniApp wxMiniApp = systemConfig.getWxMiniApp();
        wxMaDefaultConfigImpl.setAppid(wxMiniApp.getAppId());     //小程序appId
        wxMaDefaultConfigImpl.setSecret(wxMiniApp.getSecret());   //小程序secret
        wxMaService.setWxMaConfig(wxMaDefaultConfigImpl);
        return wxMaService;
    }
}
