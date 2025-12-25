package com.center.medical.workflow.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.model.WorkflowCase;
import com.center.medical.workflow.bean.param.WorkflowCaseParam;
import org.apache.ibatis.annotations.Param;

/**
 * 工作流实例记录(WorkflowCase)数据库访问层
 *
 * @author makejava
 * @since 2023-11-08 16:58:10
 */
public interface WorkflowCaseMapper extends BaseMapper<WorkflowCase> {

    /**
     * 分页查询[工作流实例记录]列表
     *
     * @param page  分页参数
     * @param param WorkflowCase查询参数
     * @return 分页数据
     */
    IPage<WorkflowCase> getPage(PageParam<WorkflowCase> page, @Param("param") WorkflowCaseParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WorkflowCase getInfoById(@Param("id") String id);

}
