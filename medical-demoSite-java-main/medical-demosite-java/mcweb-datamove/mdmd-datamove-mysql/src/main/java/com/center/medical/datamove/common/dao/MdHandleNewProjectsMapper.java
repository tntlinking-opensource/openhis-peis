package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdHandleNewProjects;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS检验科加项处理(MdHandleNewProjects)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:21
 */
public interface MdHandleNewProjectsMapper extends BaseMapper<MdHandleNewProjects> {

    /**
     * 分页查询[KS检验科加项处理]列表
     *
     * @param page  分页参数
     * @param param MdHandleNewProjects查询参数
     * @return 分页数据
     */
    IPage<MdHandleNewProjects> getPage(PageParam<MdHandleNewProjects> page, @Param("param") MdHandleNewProjects param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdHandleNewProjects getInfoById(@Param("id") String id);

}
