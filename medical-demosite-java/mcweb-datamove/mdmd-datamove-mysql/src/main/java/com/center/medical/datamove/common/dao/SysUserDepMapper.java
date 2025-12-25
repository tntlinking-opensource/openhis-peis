package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysUserDep;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户关联的科室(SysUserDep)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:40
 */
public interface SysUserDepMapper extends BaseMapper<SysUserDep> {

    /**
     * 分页查询[系统用户关联的科室]列表
     *
     * @param page  分页参数
     * @param param SysUserDep查询参数
     * @return 分页数据
     */
    IPage<SysUserDep> getPage(PageParam<SysUserDep> page, @Param("param") SysUserDep param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysUserDep getInfoById(@Param("id") String id);

}
