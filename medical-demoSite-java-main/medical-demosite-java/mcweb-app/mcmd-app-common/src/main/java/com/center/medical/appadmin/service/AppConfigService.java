package com.center.medical.appadmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.appadmin.bean.model.SysConfig;
import com.center.medical.appadmin.bean.param.ConfigPageParam;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 系统配置信息表(SysConfig)服务接口
 *
 * @author ay
 * @since 2024-03-19 17:40:46
 */
public interface AppConfigService extends IService<SysConfig> {

    /**
     * 分页查询[系统配置信息表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SysConfig> getPage(PageParam<SysConfig> page, ConfigPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysConfig getInfoById(Long id);

    /**
     * 添加或修改
     * @param sysConfig
     * @return
     */
    Boolean saOrUp(SysConfig sysConfig);

    /**
     * 删除配置
     * @param ids
     * @return
     */
    Boolean deleteByIds(List<Long> ids);
}

