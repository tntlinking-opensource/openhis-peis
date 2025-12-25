package com.center.medical.pay.service;

import com.center.medical.bean.param.MicropayParam;
import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import com.github.binarywang.wxpay.exception.WxPayException;

/**
 * @author: 路飞
 * @date: 2023-03-10 15:01
 * @description:
 */
public interface Payservice {

    /**
     * 微信扫码设备支付
     *
     * @param param
     * @return
     */
    WxPayMicropayResult micropay(MicropayParam param) throws WxPayException;

    /**
     * 生成微信支付二维码
     *
     * @param param
     * @return
     */
    String createQrCode(MicropayParam param) throws WxPayException;

}
