package com.center.medical.workflow.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.model.WorkflowItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工作流节点(WorkflowItem)数据库访问层
 *
 * @author makejava
 * @since 2023-11-08 16:58:11
 */
public interface WorkflowItemMapper extends BaseMapper<WorkflowItem> {

    /**
     * 分页查询[工作流节点]列表
     *
     * @param page  分页参数
     * @param param WorkflowItem查询参数
     * @return 分页数据
     */
    IPage<WorkflowItem> getPage(PageParam<WorkflowItem> page, @Param("param") WorkflowItem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键itemId
     * @return 详情信息
     */
    WorkflowItem getInfoById(@Param("id") String id);

    /**
     * 根据工作流ID获取节点列表
     *
     * @param flowId
     * @return
     */
    List<WorkflowItem> getListByFlowId(@Param("flowId") String flowId);
}
