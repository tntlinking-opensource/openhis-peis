package com.center.medical.reservation.task;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.center.medical.common.constant.SourcesConstants;
import com.center.medical.common.utils.ip.IpUtils;
import com.center.medical.outside.service.OsAppService;
import com.center.medical.reservation.bean.dto.ReservationNotifyDto;
import com.center.medical.reservation.bean.model.ReservationNotify;
import com.center.medical.reservation.service.ReservationNotifyService;
import com.center.medical.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 路飞
 * @date: 2023-02-07 10:46
 * @description: 预约相关的定时任务
 */
@Slf4j
@Component("reservationTask")
public class ReservationTask {
    @Resource
    private ReservationNotifyService reservationNotifyService;
    @Resource
    private OsAppService osAppService;
    @Resource
    private ISysConfigService iSysConfigService;

    /**
     * 向合作第三方发送预约信息变更通知
     *
     * @return
     */
    public void doNotify(Integer num, Integer duringTime) {
        log.info("-----开始执行：reservationTask.doNotify");
        Date start = new Date();
        DateTime offset = DateUtil.offset(start, DateField.MINUTE, duringTime);

        //判断是否允许IP执行
        List<String> ips = IpUtils.getInnerHostIp();
        String externalIp = IpUtils.getExternalIp();
        ips.add(externalIp);
        if (iSysConfigService.sysJobAuthIp(9L, ips)) {
            //获取待通知记录列表
            List<ReservationNotifyDto> list = reservationNotifyService.getNotifyList(num);
            for (ReservationNotifyDto item : list) {
                Date now1 = new Date();
                if (now1.after(offset)) {
                    log.info("向合作第三方发送预约信息变更通知定时任务，提前结束，超时了");
                    break;
                }
                Boolean result = Boolean.FALSE;
                if (item.getSystemId().equals(SourcesConstants.SOURCE_ID_APP)) {
                    // 向预约小程序发送预约状态改变请求
                    Map<String, Object> params = new HashMap<>();
                    params.put("id", item.getBizId());
                    params.put("status", item.getStatus() == -1 ? -1 : (item.getStatus() == 1 ? 1 : (item.getStatus() == 2 ? 2 : item.getStatus() == 3 ? 6 : -1)));
                    params.put("reason", item.getFailReason());
                    result = osAppService.updateRVStatus(params);

                }
                if (item.getSystemId().equals(SourcesConstants.SOURCE_ID_KANGTAO)) {
                    //TODO wait 向康淘发送预约状态改变请求
                }
                if (item.getSystemId().equals(SourcesConstants.SOURCE_ID_KANGTAO)) {
                    //TODO wait 向平安好医生发送预约状态改变请求
                }
                if (result) {
                    ReservationNotify notify = new ReservationNotify();
                    notify.setId(item.getId());
                    notify.setStatus(1);
                    reservationNotifyService.updateById(notify);
                }
            }
            log.info("-----结束执行：reservationTask.doNotify");
        }
    }
}
