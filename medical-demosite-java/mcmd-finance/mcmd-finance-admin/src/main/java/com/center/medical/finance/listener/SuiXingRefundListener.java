package com.center.medical.finance.listener;

import cn.hutool.json.JSONUtil;
import com.center.medical.bean.event.SuiXingRefundEvent;
import com.center.medical.bean.param.SuiXingTradeRefundParam;
import com.center.medical.pay.service.SuiXingPayService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 随行支付 退款
 */
@Slf4j
@Component("SuiXingRefundListener")
@AllArgsConstructor
public class SuiXingRefundListener {

    private final SuiXingPayService suiXingPayService;

    @Async
    @EventListener(SuiXingRefundEvent.class)
    public void addBranchListener(SuiXingRefundEvent event) throws Exception {
        SuiXingTradeRefundParam param = event.getParam();
        log.info("随行支付退款：{}", JSONUtil.toJsonStr(event));
        Map<String, Object> map = suiXingPayService.TradeRefund(param);
        param.setPayResult(map);
    }
}
