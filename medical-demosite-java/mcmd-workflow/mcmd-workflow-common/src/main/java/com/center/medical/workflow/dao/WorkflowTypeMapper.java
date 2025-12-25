package com.center.medical.workflow.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.model.WorkflowType;
import org.apache.ibatis.annotations.Param;

/**
 * 工作流类型(WorkflowType)数据库访问层
 *
 * @author makejava
 * @since 2023-11-08 16:58:12
 */
public interface WorkflowTypeMapper extends BaseMapper<WorkflowType> {

    /**
     * 分页查询[工作流类型]列表
     *
     * @param page  分页参数
     * @param param WorkflowType查询参数
     * @return 分页数据
     */
    IPage<WorkflowType> getPage(PageParam<WorkflowType> page, @Param("param") WorkflowType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键typeId
     * @return 详情信息
     */
    WorkflowType getInfoById(@Param("id") Integer id);

}
