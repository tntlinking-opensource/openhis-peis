package com.center.medical.sync.service;

import java.util.Set;

/**
 * 线下数据同步操作业务接口
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:31
 */
public interface SyncOfflineService {

    /**
     * 获取同步数据
     *
     * @param set 同步数据操作记录
     * @return
     */
    void getSyncContent(Set<Object> set);
}

