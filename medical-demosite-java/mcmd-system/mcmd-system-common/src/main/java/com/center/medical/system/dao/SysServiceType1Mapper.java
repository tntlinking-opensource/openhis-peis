package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysServiceType1;
import org.apache.ibatis.annotations.Param;

/**
 * 系统服务(记录系统服务种类)(SysServiceType1)数据库访问层
 *
 * @author makejava
 * @since 2024-01-23 11:02:55
 */
public interface SysServiceType1Mapper extends BaseMapper<SysServiceType1> {

    /**
     * 分页查询[系统服务(记录系统服务种类)]列表
     *
     * @param page  分页参数
     * @param param SysServiceType查询参数
     * @return 分页数据
     */
    IPage<SysServiceType1> getPage(PageParam<SysServiceType1> page, @Param("param") SysServiceType1 param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键serviceId
     * @return 详情信息
     */
    SysServiceType1 getInfoById(@Param("id") Integer id);

}
