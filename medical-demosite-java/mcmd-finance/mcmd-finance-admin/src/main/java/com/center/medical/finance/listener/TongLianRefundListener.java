package com.center.medical.finance.listener;

import cn.hutool.json.JSONUtil;
import com.center.medical.bean.event.TongLianRefundEvent;
import com.center.medical.bean.param.TongLianRefundParam;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.pay.service.TongLianPayService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
/**
 * 通联支付 退款
 */
@Slf4j
@Component("tongLianRefundListener")
@AllArgsConstructor
public class TongLianRefundListener {

    private final TongLianPayService tongLianPayService;

    @Async
    @EventListener(TongLianRefundEvent.class)
    public void addBranchListener(TongLianRefundEvent event) throws Exception {
        TongLianRefundParam param = event.getParam();
        log.info("通联支付退款：{}", JSONUtil.toJsonStr(event));
        Map<String, String> map = tongLianPayService.refund(param.getTrxamt(), param.getReqsn(), param.getOldtrxid(), "",param.getType());
        log.info("通联支付退款返回数据：{}", map);
        //效验返回数据
        if ("SUCCESS".equals(map.get("retcode"))){
            //0000是交易成功,其他都是失败
            if (!"0000".equals(map.get("trxstatus").toString())){
                log.info("退款trxstatus返回失败!");
                throw new ServiceException("退款trxstatus返回失败!");
            }
        } else {
            log.info("退款retcode返回失败!");
            throw new ServiceException("退款retcode返回失败!");
        }
        param.setPayResult(map);
    }
}
