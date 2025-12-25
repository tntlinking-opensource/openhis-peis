package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.SysDept;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）(SysDept)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:34
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 分页查询[沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）]列表
     *
     * @param page  分页参数
     * @param param SysDept查询参数
     * @return 分页数据
     */
    IPage<SysDept> getPage(PageParam<SysDept> page, @Param("param") SysDept param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键deptId
     * @return 详情信息
     */
    SysDept getInfoById(@Param("id") Long id);

}
