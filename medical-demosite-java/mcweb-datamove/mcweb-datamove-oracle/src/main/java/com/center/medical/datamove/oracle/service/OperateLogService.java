package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.OperateLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 操作日志(OperateLog)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:22
 */
public interface OperateLogService extends IService<OperateLog> {

    /**
     * 分页查询[操作日志]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OperateLog> getPage(PageParam<OperateLog> page, OperateLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OperateLog getInfoById(String id);

}

