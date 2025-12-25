package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.SysConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 参数配置表(SysConfig)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:34
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 分页查询[参数配置表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysConfig> getPage(PageParam<SysConfig> page, SysConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键configId
     * @return 详情信息
     */
    SysConfig getInfoById(Integer id);

}

