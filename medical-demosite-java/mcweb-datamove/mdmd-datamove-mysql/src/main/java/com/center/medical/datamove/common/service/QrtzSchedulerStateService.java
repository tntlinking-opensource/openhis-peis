package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.QrtzSchedulerState;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 调度器状态表(QrtzSchedulerState)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:31
 */
public interface QrtzSchedulerStateService extends IService<QrtzSchedulerState> {

    /**
     * 分页查询[调度器状态表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<QrtzSchedulerState> getPage(PageParam<QrtzSchedulerState> page, QrtzSchedulerState param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键schedName
     * @return 详情信息
     */
    QrtzSchedulerState getInfoById(String id);

}

