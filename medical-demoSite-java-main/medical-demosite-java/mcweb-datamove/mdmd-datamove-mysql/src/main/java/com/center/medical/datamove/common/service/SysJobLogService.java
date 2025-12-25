package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysJobLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 定时任务调度日志表(SysJobLog)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:35
 */
public interface SysJobLogService extends IService<SysJobLog> {

    /**
     * 分页查询[定时任务调度日志表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysJobLog> getPage(PageParam<SysJobLog> page, SysJobLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键jobLogId
     * @return 详情信息
     */
    SysJobLog getInfoById(Long id);

}

