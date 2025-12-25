package com.center.medical.platform.task;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ip.IpUtils;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.pacslis.service.MiddleDbInterfaceService;
import com.center.medical.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-02-07 10:46
 * @description: Lis相关的定时任务
 */
@Slf4j
@Component("dataUtilTask")
@DisallowConcurrentExecution
public class DataUtilTask {
    @Resource
    private ISysConfigService iSysConfigService;
    @Resource
    private MiddleDbInterfaceService middleDbInterfaceService;

    /**
     * 数据重发（将插入中间库失败的进行数据重发）
     *
     * @return
     */
    public void resendMiddleDb(Integer duringTime) {
        log.info("数据重发（将插入中间库失败的进行数据重发:{}）lisTask.resendMiddleDb：开始", duringTime);
        Date start = new Date();
        DateTime offset = DateUtil.offset(start, DateField.MINUTE, duringTime);
        List<String> ips = IpUtils.getInnerHostIp();
        if (iSysConfigService.sysJobAuthIp(21L, ips)) {
            //获取插入中间库失败的记录
            Collection<String> keys = RedisUtil.keys(CacheConstants.SEND_MIDDLE_DB_FAIL_KEY + "*");
            // log.info("插入中间库失败的记录keys：{}", JSONUtil.toJsonStr(keys));
            if (keys.size() > 0) {
                for (String key : keys) {
                    Date now = new Date();
                    if (now.after(offset)) {
                        log.info("数据重发（将插入中间库失败的进行数据重发）：提前结束，超时了");
                        break;
                    }
                    String patientcode = RedisUtil.get(key);
                    if (StringUtils.isNotBlank(patientcode)) {
                        middleDbInterfaceService.insert(patientcode);
                    }

                }
            }
            log.info("数据重发（将插入中间库失败的进行数据重发）：结束，执行了{}条记录" + keys);
        }
    }

}
