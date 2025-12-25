package com.center.medical.finance.listener;

import cn.hutool.json.JSONUtil;
import com.center.medical.bean.event.TongLianScanPayEvent;
import com.center.medical.bean.param.TongLianScanPayParam;
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
@Component("tongLianScanPayListener")
@AllArgsConstructor
public class TongLianScanPayListener {

    private final TongLianPayService tongLianPayService;


    @Async
    @EventListener(TongLianScanPayEvent.class)
    public void addBranchListener(TongLianScanPayEvent event) throws Exception {
        log.info("通联扫码支付：{}", JSONUtil.toJsonStr(event));
        TongLianScanPayParam param = event.getParam();
        param.setLimit_pay("");
        param.setAsinfo("");
        Map<String, String> map = tongLianPayService.scanPay(param.getTrxamt(),param.getReqsn(),param.getBody(),param.getRemark(),param.getAuthcode(),param.getLimit_pay(),param.getIdno(),param.getTruename(),param.getAsinfo(),param.getType());
        param.setPayResult(map);
    }

}
