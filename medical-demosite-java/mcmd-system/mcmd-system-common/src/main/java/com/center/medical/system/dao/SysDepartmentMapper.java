package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.system.bean.model.SysDepartment;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 沃德医疗部门总集(所有中心部门的总集)(SysDepartment)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-18 19:26:13
 */
public interface SysDepartmentMapper extends BaseMapper<SysDepartment> {

    /**
     * 分页查询[沃德医疗部门总集(所有中心部门的总集)]列表
     *
     * @param page  分页参数
     * @param param SysDepartment查询参数
     * @return 分页数据
     */
    IPage<SysDepartment> getPage(PageParam<SysDepartment> page, @Param("param") SysDepartment param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SysDepartment getInfoById(@Param("id") String id);

    /**
     * 根据输入码和用户性名获取科室
     * @param srm
     * @param username
     * @return
     */
    List<SysDepartment> getKsBySrmAndName(@Param("srm")String srm,@Param("username") String username);
}
