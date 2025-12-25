package com.center.medical.sellcrm.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.SalesTargetStatistic;
import com.center.medical.service.SalesTargetStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

/**
 * @author: ay
 * @date: 2024-01-20 14:46
 * @description: 销售目标自动统计定时任务
 */
@Slf4j
@Component("salesTargetStatisticsTask")
public class SalesTargetStatisticsTask {

    @Resource
    private SalesTargetStatisticService salesTargetStatisticService;


    /**
     *
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void start() {
        Calendar c=Calendar.getInstance();
        int yearEnd=c.get(Calendar.YEAR);
//        int yearStart= StringUtils.isNotEmpty(yearStr)?Integer.parseInt(yearStr):yearEnd;
        int yearStart = yearEnd;
        while(yearStart<=yearEnd){
            String year=yearStart+"";
            //清除老数据
            salesTargetStatisticService.remove(new LambdaQueryWrapper<SalesTargetStatistic>().eq(SalesTargetStatistic::getYear,year));

            //查询并添加
            List<SalesTargetStatistic> leaderList = salesTargetStatisticService.selectLeaderList(year);
            log.info("年度计划共"+leaderList.size()+"条");
            for(SalesTargetStatistic salesTargetStatistic:leaderList){
                salesTargetStatistic.setYear(year);
                salesTargetStatisticService.save(salesTargetStatistic);
            }
            List<SalesTargetStatistic> sellList = salesTargetStatisticService.selectSellList(year);
            log.info("季度计划共"+sellList.size()+"条");
            for(SalesTargetStatistic salesTargetStatistic:sellList){
                salesTargetStatistic.setYear(year);
                salesTargetStatisticService.save(salesTargetStatistic);
            }
            List<SalesTargetStatistic> monthList = salesTargetStatisticService.selectMonthList(year);
            log.info("月度计划共"+monthList.size()+"条");
            for(SalesTargetStatistic salesTargetStatistic:monthList){
                salesTargetStatistic.setYear(year);
                salesTargetStatisticService.save(salesTargetStatistic);
            }
            yearStart++;
        }
    }


}
