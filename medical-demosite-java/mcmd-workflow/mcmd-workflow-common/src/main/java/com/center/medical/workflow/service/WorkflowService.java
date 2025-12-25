package com.center.medical.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.model.Workflow;
import com.center.medical.workflow.bean.param.WorkflowParam;
import com.center.medical.workflow.bean.vo.WorkflowVo;

import java.util.List;

/**
 * 工作流(Workflow)服务接口
 *
 * @author makejava
 * @since 2023-11-08 16:58:10
 */
public interface WorkflowService extends IService<Workflow> {

    /**
     * 分页查询[工作流]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WorkflowVo> getPage(PageParam<Workflow> page, WorkflowParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Workflow getInfoById(String id);

    /**
     * 新增或者更新
     *
     * @param workflow
     * @return
     */
    Boolean saOrUp(Workflow workflow);

    /**
     * 删除工作流
     *
     * @param ids 工作流ID集合
     * @return
     */
    Boolean delByIds(List<String> ids);

    /**
     * 判断是否开启工作流
     *
     * @param cId      分中心ID
     * @param typeFlag 工作流类型标识
     * @return
     */
    Workflow isOpen(String cId, String typeFlag);
}

