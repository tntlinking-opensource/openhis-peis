package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersion1;
import org.apache.ibatis.annotations.Param;

/**
 * 自动部署-更新版本信息(SysVersion1)数据库访问层
 *
 * @author makejava
 * @since 2024-01-23 10:36:16
 */
public interface SysVersion1Mapper extends BaseMapper<SysVersion1> {

    /**
     * 分页查询[自动部署-更新版本信息]列表
     *
     * @param page  分页参数
     * @param param SysVersion查询参数
     * @return 分页数据
     */
    IPage<SysVersion1> getPage(PageParam<SysVersion1> page, @Param("param") SysVersion1 param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysVersion1 getInfoById(@Param("id") Integer id);

}
