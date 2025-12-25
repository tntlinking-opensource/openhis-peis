package com.center.medical.reservation.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.common.utils.ip.IpUtils;
import com.center.medical.reservation.bean.model.ReservationDefault;
import com.center.medical.reservation.bean.model.ReservationSetting;
import com.center.medical.reservation.service.ReservationDefaultService;
import com.center.medical.reservation.service.ReservationSettingService;
import com.center.medical.system.service.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 预约设置定时任务
 */
@Component("reservationSettingTask")
public class ReservationSettingTask {
    private static final Logger log = LoggerFactory.getLogger(ReservationSettingTask.class);
    @Resource
    private ReservationDefaultService reservationDefaultService;
    @Resource
    private ReservationSettingService reservationSettingService;
    @Resource
    private ISysConfigService iSysConfigService;


    /**
     * 预约默认设置定时任务
     * 每天读取一次,然后再添加到预约设置里面
     */
    public void inset(Integer endDay) {
        //判断是否允许IP执行
        List<String> ips = IpUtils.getInnerHostIp();
        String externalIp = IpUtils.getExternalIp();
        ips.add(externalIp);
        if (iSysConfigService.sysJobAuthIp(6L, ips)) {
            log.info("预约默认设置定时任务执行中endDay={}--------------{}", endDay);
            List<ReservationSetting> upList = new ArrayList<>();
            //1.查询预约默认设置
            List<ReservationDefault> list = reservationDefaultService.list(new LambdaQueryWrapper<ReservationDefault>()
                    .eq(ReservationDefault::getStatus, 1));
            //生成未来endDay天预约设置
            for (int i = 0; i < endDay; i++) {
                //2.获取插入数据
                for (ReservationDefault reservationDefault : list) {
                    DateTime reservationDate = DateUtil.beginOfDay(new Date());
                    //获取今天是星期几
                    DateTime dateTime = DateUtil.offsetDay(reservationDate, i);
                    LocalDateTime localDateTime = DateUtil.toLocalDateTime(dateTime);
                    DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
                    int num = dayOfWeek.getValue();
                    //判断是否在里面
                    if (reservationDefault.getOpenDay().contains(String.valueOf(num))) {
                        //判断是否已经存在了记录
                        //预约日期reservation_date,预约类型level_id,状态status相同视为重复
                        long count = reservationSettingService.count(new LambdaQueryWrapper<ReservationSetting>()
                                .eq(ReservationSetting::getReservationDate, dateTime)
                                .eq(ReservationSetting::getLevelId, reservationDefault.getLevelId())
                                .eq(ReservationSetting::getBranchId, reservationDefault.getBranchId())
//                                .eq(ReservationSetting::getStatus, 1)
                        );
                        if (count > 0) {
                            continue;
                        }
                        //新建预约设置
                        ReservationSetting reservationSetting = new ReservationSetting();
                        reservationSetting.setReservationDate(dateTime);
                        reservationSetting.setStartTime(reservationDefault.getStartTime());
                        reservationSetting.setEndTime(reservationDefault.getEndTime());
                        reservationSetting.setMaxNum(reservationDefault.getMaxNum());
                        reservationSetting.setAbleNum(reservationDefault.getMaxNum());
                        reservationSetting.setDoneNum(0);
                        reservationSetting.setLevelId(reservationDefault.getLevelId());
                        reservationSetting.setRemark(reservationDefault.getRemark());
                        reservationSetting.setBranchId(reservationDefault.getBranchId());
                        reservationSetting.setStatus(1);
                        reservationSetting.setCreatedate(new Date());
                        reservationSetting.setCreator("定时任务");
                        upList.add(reservationSetting);
                    }
                }
            }

            //3.批量插入
            reservationSettingService.saveOrUpdateBatch(upList);
        }
    }

    /**
     * 预约设置
     * 把过期的数据预约开放状态改为过期预约
     */
    public void close() {
        log.info("开始执行预约设置过期任务--------------");

        //判断是否允许IP执行
        List<String> ips = IpUtils.getInnerHostIp();
        String externalIp = IpUtils.getExternalIp();
        ips.add(externalIp);
        if (iSysConfigService.sysJobAuthIp(5L, ips)) {
            List<ReservationSetting> upList = new ArrayList<>();
            //1.获取还是开放状态的数据 状态：0.关闭 1.开放
            List<ReservationSetting> list = reservationSettingService.list(new LambdaQueryWrapper<ReservationSetting>()
                    .eq(ReservationSetting::getStatus, 1));
            //2.判断是否过期
            for (ReservationSetting reservationSetting : list) {
                Date endTime = DateUtil.parse(DateUtil.format(reservationSetting.getReservationDate(), "yyyy-MM-dd") + " " + reservationSetting.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                //结束时间在当前时间之前的
                if (endTime.before(new Date())) {
                    reservationSetting.setStatus(2);
                    reservationSetting.setModifydate(new Date());
                    reservationSetting.setModifier("定时任务到期自动取消");
                    upList.add(reservationSetting);
                }
            }
            //3.批量更新
            reservationSettingService.updateBatchById(upList);
        }
    }


}
