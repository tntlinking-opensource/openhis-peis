package com.center.medical.quartz.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.center.medical.olddata.service.VCheckReqmainMysqlService;
import org.quartz.DisallowConcurrentExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 定时删除V_CHECK_REQMAIN_MYSQL表里面的数据
 */
@Component("checkReqmainTask")
@DisallowConcurrentExecution
public class CheckReqmainTask {
    private static final Logger log = LoggerFactory.getLogger(CheckReqmainTask.class);
    @Resource
    private VCheckReqmainMysqlService vCheckReqmainMysqlService;


    /**
     * 定时删除V_CHECK_REQMAIN_MYSQL表里面的数据
     */
    public void delete(Integer day) {
        //当前时间减x天的00点
        DateTime dateTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -day));
        vCheckReqmainMysqlService.deleteTimeOutList(dateTime);
    }


}
