package com.center.medical.framework.listener;

import cn.hutool.json.JSONUtil;
import com.center.medical.framework.event.SyncDataEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author: 路飞
 * @date: 2022-11-17 20:47
 * @description: 数据同步操作处理
 */
@Slf4j
@Component("syncDataListener")
@AllArgsConstructor
public class SyncDataListener {

//    private final SyncDataService syncDataService;

    @EventListener(SyncDataEvent.class)
    public void syncDataListener(SyncDataEvent event) {
        log.info("数据同步操作记录：{}", JSONUtil.toJsonStr(event));
//        syncDataService.save(event.getSyncData());
    }
}
