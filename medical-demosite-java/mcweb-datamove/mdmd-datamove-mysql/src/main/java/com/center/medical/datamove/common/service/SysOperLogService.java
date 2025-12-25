package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysOperLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 操作日志记录(SysOperLog)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:36
 */
public interface SysOperLogService extends IService<SysOperLog> {

    /**
     * 分页查询[操作日志记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysOperLog> getPage(PageParam<SysOperLog> page, SysOperLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键operId
     * @return 详情信息
     */
    SysOperLog getInfoById(Long id);

}

