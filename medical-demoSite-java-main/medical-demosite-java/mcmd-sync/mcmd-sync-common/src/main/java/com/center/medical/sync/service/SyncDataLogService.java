package com.center.medical.sync.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sync.bean.model.SyncDataLog;

/**
 * 同步数据操作记录(SyncDataLog)表服务接口
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:33
 */
public interface SyncDataLogService extends IService<SyncDataLog> {

    /**
     * 分页查询[同步数据操作记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SyncDataLog> getPage(PageParam<SyncDataLog> page, SyncDataLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键logId
     * @return 详情信息
     */
    SyncDataLog getInfoById(Long id);

}

