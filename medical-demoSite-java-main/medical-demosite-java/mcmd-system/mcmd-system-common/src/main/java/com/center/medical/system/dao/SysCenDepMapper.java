package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.domain.entity.SysCenDep;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 分中心部门表(SysCenDep)表数据库访问层
 *
 * @author ay
 * @since 2023-04-26 08:43:56
 */
public interface SysCenDepMapper extends BaseMapper<SysCenDep> {

    /**
     * 分页查询[分中心部门表]列表
     *
     * @param page  分页参数
     * @param param SysCenDep查询参数
     * @return 分页数据
     */
    IPage<SysCenDep> getList(PageParam<SysCenDep> page, @Param("param") SysCenDep param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysCenDep getInfoById(@Param("id") String id);

}
