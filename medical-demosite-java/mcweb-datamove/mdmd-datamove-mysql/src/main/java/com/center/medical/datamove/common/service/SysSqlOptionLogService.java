package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysSqlOptionLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * SQL操作日志表(SysSqlOptionLog)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:38
 */
public interface SysSqlOptionLogService extends IService<SysSqlOptionLog> {

    /**
     * 分页查询[SQL操作日志表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysSqlOptionLog> getPage(PageParam<SysSqlOptionLog> page, SysSqlOptionLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysSqlOptionLog getInfoById(Long id);

}

