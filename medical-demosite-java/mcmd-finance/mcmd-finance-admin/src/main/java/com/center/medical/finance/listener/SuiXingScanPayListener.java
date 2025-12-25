package com.center.medical.finance.listener;

import cn.hutool.json.JSONUtil;
import com.center.medical.bean.event.SuiXingScanPayEvent;
import com.center.medical.bean.param.SuiXingReverseScanParam;
import com.center.medical.pay.service.SuiXingPayService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 随行支付 扫码支付
 */
@Slf4j
@Component("suiXingScanPayListener")
@AllArgsConstructor
public class SuiXingScanPayListener {

    private final SuiXingPayService suiXingPayService;


    @Async
    @EventListener(SuiXingScanPayEvent.class)
    public void ScanPay(SuiXingScanPayEvent event) throws Exception {
        log.info("随行支付支付参数：{}", JSONUtil.toJsonStr(event));
        SuiXingReverseScanParam param = event.getParam();
        Map<String, Object> map = suiXingPayService.scanPay(param);
        param.setPayResult(map);
    }






}
