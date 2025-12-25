package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.HandleNewProjects;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS检验科加项处理(HandleNewProjects)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:09
 */
public interface HandleNewProjectsMapper extends BaseMapper<HandleNewProjects> {

    /**
     * 分页查询[KS检验科加项处理]列表
     *
     * @param page  分页参数
     * @param param HandleNewProjects查询参数
     * @return 分页数据
     */
    IPage<HandleNewProjects> getPage(PageParam<HandleNewProjects> page, @Param("param") HandleNewProjects param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    HandleNewProjects getInfoById(@Param("id") String id);

}
