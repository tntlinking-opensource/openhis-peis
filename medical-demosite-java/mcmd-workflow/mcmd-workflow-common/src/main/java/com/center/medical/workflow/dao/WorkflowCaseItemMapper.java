package com.center.medical.workflow.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.model.WorkflowCaseItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实例节点(WorkflowCaseItem)数据库访问层
 *
 * @author makejava
 * @since 2023-11-08 16:58:11
 */
public interface WorkflowCaseItemMapper extends BaseMapper<WorkflowCaseItem> {

    /**
     * 分页查询[实例节点]列表
     *
     * @param page  分页参数
     * @param param WorkflowCaseItem查询参数
     * @return 分页数据
     */
    IPage<WorkflowCaseItem> getPage(PageParam<WorkflowCaseItem> page, @Param("param") WorkflowCaseItem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键itemId
     * @return 详情信息
     */
    WorkflowCaseItem getInfoById(@Param("id") String id);

    /**
     * 根据工实例ID获取节点列表
     *
     * @param caseId
     * @return
     */
    List<WorkflowCaseItem> getListByCaseId(@Param("caseId") String caseId);
}
