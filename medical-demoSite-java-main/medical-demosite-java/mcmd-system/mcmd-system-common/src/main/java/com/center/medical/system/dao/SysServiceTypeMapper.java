package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysServiceType;
import org.apache.ibatis.annotations.Param;

/**
 * 系统服务(记录系统服务种类)(SysServiceType)数据库访问层
 *
 * @author makejava
 * @since 2024-03-01 18:02:34
 */
public interface SysServiceTypeMapper extends BaseMapper<SysServiceType> {

    /**
     * 分页查询[系统服务(记录系统服务种类)]列表
     *
     * @param page  分页参数
     * @param param SysServiceType查询参数
     * @return 分页数据
     */
    IPage<SysServiceType> getPage(PageParam<SysServiceType> page, @Param("param") SysServiceType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键serviceId
     * @return 详情信息
     */
    SysServiceType getInfoById(@Param("id") Integer id);

}
