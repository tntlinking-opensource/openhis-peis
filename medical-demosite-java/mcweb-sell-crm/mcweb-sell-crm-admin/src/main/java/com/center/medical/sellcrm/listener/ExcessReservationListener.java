package com.center.medical.sellcrm.listener;

import com.alibaba.fastjson.JSON;
import com.center.medical.bean.event.ExcessReservationEvent;
import com.center.medical.reservation.bean.param.ReservationGroupParam;
import com.center.medical.reservation.service.ReservationGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author: ay
 * @date: 2024-3-8 12:47
 * @description: 超额预约审批
 */
@Slf4j
@Component("excessReservationListener")
@AllArgsConstructor
public class ExcessReservationListener {

    private final ReservationGroupService reservationGroupService;

    @EventListener(ExcessReservationEvent.class)
    public void start(ExcessReservationEvent event) {
        String data = event.getParam().getData();
        log.info("超额预约审批：{}", data);

        ReservationGroupParam param = JSON.parseObject(data, ReservationGroupParam.class);
//        param.setApprovalProcess("跳过审批");
//        param.setSkip(null);
        reservationGroupService.saOrUp(param);


    }
}
