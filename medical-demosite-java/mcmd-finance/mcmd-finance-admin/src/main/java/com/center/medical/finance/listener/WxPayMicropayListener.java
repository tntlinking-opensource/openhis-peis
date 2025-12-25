package com.center.medical.finance.listener;


import cn.hutool.json.JSONUtil;
import com.center.medical.bean.event.WxPayMicropayEvent;
import com.center.medical.bean.param.MicropayParam;
import com.center.medical.pay.service.Payservice;
import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 扫码支付
 */
@Slf4j
@Component("wxPayMicropayListener")
@AllArgsConstructor
public class WxPayMicropayListener {

    private final Payservice payservice;


    @Async
    @EventListener(WxPayMicropayEvent.class)
    public void addBranchListener(WxPayMicropayEvent event) throws WxPayException {
        log.info("扫码支付：{}", JSONUtil.toJsonStr(event));
        MicropayParam micropayParam = event.getMicropayParam();
        WxPayMicropayResult micropay = payservice.micropay(micropayParam);
        micropayParam.setWxPayMicropayResult(micropay);
    }
}
