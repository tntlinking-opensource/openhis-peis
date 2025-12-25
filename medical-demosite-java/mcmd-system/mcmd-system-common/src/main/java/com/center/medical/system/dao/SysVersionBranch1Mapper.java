package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysVersionBranch1;
import org.apache.ibatis.annotations.Param;

/**
 * 自动部署-版本更新分中心关联表(SysVersionBranch1)数据库访问层
 *
 * @author makejava
 * @since 2024-01-23 10:36:17
 */
public interface SysVersionBranch1Mapper extends BaseMapper<SysVersionBranch1> {

    /**
     * 分页查询[自动部署-版本更新分中心关联表]列表
     *
     * @param page  分页参数
     * @param param SysVersionBranch查询参数
     * @return 分页数据
     */
    IPage<SysVersionBranch1> getPage(PageParam<SysVersionBranch1> page, @Param("param") SysVersionBranch1 param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysVersionBranch1 getInfoById(@Param("id") Integer id);

}
