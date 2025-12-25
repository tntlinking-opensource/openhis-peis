package com.center.medical.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.model.WorkflowReservationType;
import com.center.medical.workflow.bean.param.SubmitForApprovalParam;
import com.center.medical.workflow.bean.vo.ToBeSubmittedVo;

import java.util.List;

/**
 * 工作流-预约会员类型(WorkflowReservationType)服务接口
 *
 * @author ay
 * @since 2024-03-07 13:36:10
 */
public interface WorkflowReservationTypeService extends IService<WorkflowReservationType> {

    /**
     * 分页查询[工作流-预约会员类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WorkflowReservationType> getPage(PageParam<WorkflowReservationType> page, WorkflowReservationType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WorkflowReservationType getInfoById(String id);

    /**
     * 查询待提交的审批
     * @param orderId
     * @return
     */
    List<ToBeSubmittedVo> getToBeSubmitted(String orderId,String groupId);

    /**
     * 校正会员类型
     * @param orderId
     * @return
     */
    Boolean correctMembershipType(String orderId,String groupId);

    /**
     * 提交审批
     * @param param
     * @return
     */
    Boolean submitForApproval(SubmitForApprovalParam param);

    /**
     * 根据实例ID获取审批用户
     * @param caseId
     * @return
     */
    List<ToBeSubmittedVo> getListByCaseId(String caseId);
}

