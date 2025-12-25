package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.SysUserDep;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户关联的科室(SysUserDep)表数据库访问层
 *
 * @author ay
 * @since 2023-04-06 13:33:20
 */
public interface SysUserDepMapper extends BaseMapper<SysUserDep> {

    /**
     * 分页查询[系统用户关联的科室]列表
     *
     * @param page  分页参数
     * @param param SysUserDep查询参数
     * @return 分页数据
     */
    IPage<SysUserDep> getList(PageParam<SysUserDep> page, @Param("param") SysUserDep param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysUserDep getInfoById(@Param("id") String id);

}
