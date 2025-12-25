package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysFunction;
import org.apache.ibatis.annotations.Param;

/**
 * 系统业务功能(SysFunction)数据库访问层
 *
 * @author makejava
 * @since 2024-03-19 11:12:08
 */
public interface SysFunctionMapper extends BaseMapper<SysFunction> {

    /**
     * 分页查询[系统业务功能]列表
     *
     * @param page  分页参数
     * @param param SysFunction查询参数
     * @return 分页数据
     */
    IPage<SysFunction> getPage(PageParam<SysFunction> page, @Param("param") SysFunction param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键functionId
     * @return 详情信息
     */
    SysFunction getInfoById(@Param("id") String id);

}
