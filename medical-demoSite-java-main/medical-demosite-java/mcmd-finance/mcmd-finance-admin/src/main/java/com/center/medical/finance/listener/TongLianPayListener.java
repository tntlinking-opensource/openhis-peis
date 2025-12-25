package com.center.medical.finance.listener;

import cn.hutool.json.JSONUtil;
import com.center.medical.bean.event.TongLianPayEvent;
import com.center.medical.bean.param.TongLianPayParam;
import com.center.medical.pay.service.TongLianPayService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 通联支付 扫码支付
 */
@Slf4j
@Component("tongLianPayListener")
@AllArgsConstructor
public class TongLianPayListener {

    private final TongLianPayService tongLianPayService;


    @Async
    @EventListener(TongLianPayEvent.class)
    public void addBranchListener(TongLianPayEvent event) throws Exception {
        log.info("通联扫码支付：{}", JSONUtil.toJsonStr(event));
        TongLianPayParam param = event.getParam();
//        Map<String, String> map = tongLianPayService.pay(param.getTrxamt(), param.getReqsn(), param.getPaytype(), param.getBody(), param.getRemark(), "", "123","https://test.allinpaygd.com/JWeb/NotifyServlet","","","","", "", "", "", "", "", "", "","");
        Map<String, String> map = tongLianPayService.pay(param.getTrxamt(), param.getReqsn(), param.getPaytype(), param.getBody(), param.getRemark(), "", "123", "", "", "", "", "", "", "", "", "", "", "", "", "");
        param.setPayResult(map);
    }

}
