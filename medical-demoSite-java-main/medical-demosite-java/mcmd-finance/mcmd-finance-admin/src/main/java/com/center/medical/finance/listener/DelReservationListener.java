package com.center.medical.finance.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.event.DelReservationEvent;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.model.ReservationSetting;
import com.center.medical.reservation.service.ReservationService;
import com.center.medical.reservation.service.ReservationSettingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 删除预约信息
 */
@Slf4j
@Component("delReservationListener")
@AllArgsConstructor
public class DelReservationListener {

    private final ReservationService reservationService;
    private final ReservationSettingService reservationSettingService;


    @Async
    @EventListener(DelReservationEvent.class)
    public void del(DelReservationEvent event) {
        log.info("--------开始删除预约信息--------");
        Reservation reservation = reservationService.getOne(new LambdaQueryWrapper<Reservation>()
                .eq(Reservation::getPatientcode, event.getPatientCode())
                .in(Reservation::getStatus, 0, 1, 2, 3)
        );
        if (ObjectUtils.isNotEmpty(reservation) && StringUtils.isNotEmpty(reservation.getTimeId())){
            // 恢复可预约人数
            ReservationSetting setting = reservationSettingService.getInfoById(reservation.getTimeId());
            if (Objects.nonNull(setting)) {
                reservationSettingService.updateAbleNum(reservation.getTimeId(), 1);
            }
            //删除预约信息
            reservationService.remove(new LambdaQueryWrapper<Reservation>()
                    .eq(Reservation::getPatientcode,event.getPatientCode()));
        }
        log.info("--------预约信息删除成功--------");
    }





}
