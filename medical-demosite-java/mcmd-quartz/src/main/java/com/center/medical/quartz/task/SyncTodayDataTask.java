package com.center.medical.quartz.task;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.utils.ip.IpUtils;
import com.center.medical.reception.service.OrderService;
import com.center.medical.reception.service.RegisterService;
import com.center.medical.reception.service.SyncCommonService;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 路飞
 * @date: 2024-06-07 10:46
 * @description: 同步当天数据定时任务
 */
@Slf4j
@Component("syncTodayDataTask")
@DisallowConcurrentExecution
public class SyncTodayDataTask {


    @Resource
    private OrderService orderService;
    @Resource
    private ISysConfigService iSysConfigService;
    @Resource
    private RegisterService registerService;
    @Resource
    private SyncCommonService syncCommonService;


    /**
     * 同步当天变化的订单（每天晚上9点以后）
     * @param taskId     定时任务ID
     */
    public void syncOrderData(Long taskId) {
        log.info("--------------同步当天订单信息--------------");
        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(taskId, ips)) {
            //获取当天变化的订单
            List<Createorder> list = orderService.list(new LambdaQueryWrapper<Createorder>().ge(Createorder::getModifydate, DateUtil.beginOfDay(new Date())));
            if (CollectionUtil.isNotEmpty(list)){
                List<String> orderIds = list.stream().map(Createorder::getDdh).collect(Collectors.toList());
                syncCommonService.syncOrderData(orderIds);
            }
        }
    }


    /**
     * 同步当天体检者信息（每天晚上9点半以后）
     * @param taskId     定时任务ID
     */
    public void syncPatientData(Long taskId) {
        log.info("--------------同步当天体检者信息--------------");
        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(taskId, ips)) {
            //获取当天需要同步的体检者
            List<Peispatient> list;
            if (ZhongkangConfig.isOnline()){
                //线上同步至线下，获取未登记的
                list = registerService.list(new LambdaQueryWrapper<Peispatient>().ge(Peispatient::getCreatedate, DateUtil.beginOfDay(new Date())).eq(Peispatient::getFRegistered, 0));
            }else {
                //线下同步至线上，获取已登记的
                list = registerService.list(new LambdaQueryWrapper<Peispatient>().ge(Peispatient::getDateregister, DateUtil.beginOfDay(new Date())).eq(Peispatient::getFRegistered, 1));
            }

            if (CollectionUtil.isNotEmpty(list)){
                List<String> patientCodes = list.stream().map(Peispatient::getPatientcode).collect(Collectors.toList());
                syncCommonService.syncPatientData(patientCodes);

            }
        }
    }


    /**
     * 删除已登记体检者在其他分中心数据（每天晚上10点半以后），只在线上系统执行
     * 多中心备单的体检者登记后，删除其他非到检的中心的体检数据
     * @param taskId     定时任务ID
     */
    public void delOtherPatient(Long taskId) {
        log.info("--------------删除已登记体检者在其他分中心数据--------------");
        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(taskId, ips)) {
            //获取当天需要同步的体检者
            List<Peispatient> list;
            if (ZhongkangConfig.isOnline()){
                //线上，获取当天登记的体检者
                list = registerService.list(new LambdaQueryWrapper<Peispatient>().ge(Peispatient::getDateregister, DateUtil.beginOfDay(new Date())).eq(Peispatient::getFRegistered, 1));
                if (CollectionUtil.isNotEmpty(list)){
                    List<String> patientCodes = list.stream().map(Peispatient::getPatientcode).collect(Collectors.toList());
                    syncCommonService.delOtherPatient(patientCodes);

                }
            }

        }
    }
}
