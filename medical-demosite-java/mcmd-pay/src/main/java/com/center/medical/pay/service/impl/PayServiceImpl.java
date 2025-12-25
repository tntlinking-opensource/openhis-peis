package com.center.medical.pay.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.center.medical.bean.param.MicropayParam;
import com.center.medical.common.enums.PayType;
import com.center.medical.common.utils.Arith;
import com.center.medical.pay.service.Payservice;
import com.center.medical.system.config.WxConfig;
import com.center.medical.system.service.ISysConfigService;
import com.github.binarywang.wxpay.bean.order.WxPayNativeOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayMicropayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

/**
 * @author: 路飞
 * @date: 2023-03-10 15:03
 * @description:
 */
@Slf4j
@Service("paysService")
@RequiredArgsConstructor
public class PayServiceImpl implements Payservice {
    private final WxConfig wxConfig;
    private final MapperFacade mapperFacade;
    private final ISysConfigService iSysConfigService;
    private final Snowflake snowflake;

    /**
     * 微信扫码设备支付
     *
     * @param param
     * @return
     */
    @Override
    public WxPayMicropayResult micropay(MicropayParam param) throws WxPayException {
        WxPayMicropayRequest request = mapperFacade.map(param, WxPayMicropayRequest.class);
        log.info("微信扫码设备支付参数：{}、{}", param, request);
        request.setSpbillCreateIp("127.0.0.1");
        WxPayMicropayResult micropay = wxConfig.getWxPayService(PayType.WECHATPAY).micropay(request);
        log.info("微信扫码设备支付结果：{}", micropay);
        return micropay;
    }

    @Override
    public String createQrCode(MicropayParam param) throws WxPayException {
        WxPayService wxPayService = wxConfig.getWxPayService(PayType.WECHATPAY_SWEEP_CODE);
        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        orderRequest.setBody(param.getBody());
        orderRequest.setOutTradeNo(param.getOutTradeNo());
        orderRequest.setTotalFee((int) Arith.mul(param.getTotalFee(), 100));
        orderRequest.setSpbillCreateIp(param.getBodspbillCreateIpy());
        // orderRequest.setOpenid(param.getBizUserId());
        String notifyUrl = iSysConfigService.getDomain().getPlatformDomain() + "/notice/pay/order/" + PayType.WECHATPAY_SWEEP_CODE.value();
        orderRequest.setNotifyUrl(notifyUrl);
        // 支付单号
        String payNo = String.valueOf(snowflake.nextId());
        orderRequest.setProductId(payNo);
        // 生成微信二维码
        orderRequest.setTradeType(WxPayConstants.TradeType.NATIVE);
        WxPayNativeOrderResult wxPayNativeOrderResult = wxPayService.createOrder(orderRequest);

        return wxPayNativeOrderResult.getCodeUrl();
    }
}
