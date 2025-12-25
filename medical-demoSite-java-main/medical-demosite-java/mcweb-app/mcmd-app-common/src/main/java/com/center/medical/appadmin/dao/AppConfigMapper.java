package com.center.medical.appadmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.SysConfig;
import com.center.medical.appadmin.bean.param.ConfigPageParam;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置信息表(SysConfig)数据库访问层
 *
 * @author ay
 * @since 2024-03-19 17:40:42
 */
public interface AppConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 分页查询[系统配置信息表]列表
     *
     * @param page  分页参数
     * @param param SysConfig查询参数
     * @return 分页数据
     */
    IPage<SysConfig> getPage(PageParam<SysConfig> page, @Param("param") ConfigPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysConfig getInfoById(@Param("id") Long id);

}
