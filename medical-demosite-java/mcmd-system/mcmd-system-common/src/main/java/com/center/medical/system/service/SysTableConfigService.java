package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysTableConfig;

/**
 * 数据表配置(SysTableConfig)服务接口
 *
 * @author makejava
 * @since 2023-07-05 15:26:06
 */
public interface SysTableConfigService extends IService<SysTableConfig> {

    void init();

    /**
     * 分页查询[数据表配置]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysTableConfig> getPage(PageParam<SysTableConfig> page, SysTableConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysTableConfig getInfoById(Integer id);

    /**
     * 根据表名获取数据表配置信息
     *
     * @param tableName
     * @return
     */
    SysTableConfig getInfoByTN(String tableName);

    /**
     * 删除缓存
     *
     * @param tableName
     */
    void removeSysTableConfig(String tableName);
}

