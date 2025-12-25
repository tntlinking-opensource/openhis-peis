package com.center.medical.workflow.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.model.Workflow;
import com.center.medical.workflow.bean.param.WorkflowParam;
import com.center.medical.workflow.bean.vo.WorkflowVo;
import org.apache.ibatis.annotations.Param;

/**
 * 工作流(Workflow)数据库访问层
 *
 * @author makejava
 * @since 2023-11-08 16:58:09
 */
public interface WorkflowMapper extends BaseMapper<Workflow> {

    /**
     * 分页查询[工作流]列表
     *
     * @param page  分页参数
     * @param param Workflow查询参数
     * @return 分页数据
     */
    IPage<WorkflowVo> getPage(PageParam<Workflow> page, @Param("param") WorkflowParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Workflow getInfoById(@Param("id") String id);

}
