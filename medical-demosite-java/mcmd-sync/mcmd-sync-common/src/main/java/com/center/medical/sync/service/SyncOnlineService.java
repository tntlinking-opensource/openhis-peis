package com.center.medical.sync.service;

import com.center.medical.bean.model.SysBranch;
import com.center.medical.sync.bean.dto.SyncDataDto;

import java.util.List;
import java.util.Set;

/**
 * 线上数据同步操作业务接口
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:31
 */
public interface SyncOnlineService {

    /**
     * 获取同步数据
     *
     * @param set 同步数据操作记录
     * @return
     */
    void getSyncContent(Set<Object> set);

    /**
     * 手动同步
     * @param syncDataDtos 同步数据
     * @param branch 分中心
     * @return
     */
    Boolean execSync(List<SyncDataDto> syncDataDtos, SysBranch branch);
}

