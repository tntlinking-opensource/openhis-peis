package com.center.medical.sellcrm.listener;

import cn.hutool.json.JSONUtil;
import com.center.medical.bean.event.OrderFlowEvent;
import com.center.medical.bean.param.OrderFlowParam;
import com.center.medical.sellcrm.bean.param.CheckOrderParam;
import com.center.medical.sellcrm.service.CreateorderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: 路飞
 * @date: 2022-11-17 20:47
 * @description: 订单事件处理
 */
@Slf4j
@Component("orderListener")
@AllArgsConstructor
public class OrderListener {

    private final CreateorderService createorderService;
    private final MapperFacade mapperFacade;

    @EventListener(OrderFlowEvent.class)
    public void orderFlowDeal(OrderFlowEvent event) {
        log.info("订单审核结果处理：{}", JSONUtil.toJsonStr(event));
        OrderFlowParam param = event.getParam();
        CheckOrderParam checkOrderParam = mapperFacade.map(param, CheckOrderParam.class);

        if (Objects.nonNull(param)) {
            if ("1".equals(param.getFchange())) {
                //如果是变更审核
                createorderService.checkChange(checkOrderParam);
            } else {
                //如果是普通审核
                createorderService.checkOrder(checkOrderParam);
            }
        }
    }
}
