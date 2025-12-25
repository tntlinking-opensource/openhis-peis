package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 参数配置表(SysConfig)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:33
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 分页查询[参数配置表]列表
     *
     * @param page  分页参数
     * @param param SysConfig查询参数
     * @return 分页数据
     */
    IPage<SysConfig> getPage(PageParam<SysConfig> page, @Param("param") SysConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键configId
     * @return 详情信息
     */
    SysConfig getInfoById(@Param("id") Integer id);

}
