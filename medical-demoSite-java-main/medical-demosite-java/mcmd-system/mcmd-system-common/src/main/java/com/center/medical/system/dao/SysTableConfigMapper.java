package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysTableConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 数据表配置(SysTableConfig)数据库访问层
 *
 * @author makejava
 * @since 2023-07-05 15:26:06
 */
public interface SysTableConfigMapper extends BaseMapper<SysTableConfig> {

    /**
     * 分页查询[数据表配置]列表
     *
     * @param page  分页参数
     * @param param SysTableConfig查询参数
     * @return 分页数据
     */
    IPage<SysTableConfig> getPage(PageParam<SysTableConfig> page, @Param("param") SysTableConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysTableConfig getInfoById(@Param("id") Integer id);

}
