package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.SqlOptionLog;
import com.center.medical.common.utils.page.PageParam;

/**
 * SQL操作日志表(SqlOptionLog)表服务接口
 *
 * @author 路飞船长
 * @since 2023-06-21 14:33:20
 */
public interface SqlOptionLogService extends IService<SqlOptionLog> {

    /**
     * 分页查询[SQL操作日志表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SqlOptionLog> getPage(PageParam<SqlOptionLog> page, SqlOptionLog param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SqlOptionLog getInfoById(Long id);

}

