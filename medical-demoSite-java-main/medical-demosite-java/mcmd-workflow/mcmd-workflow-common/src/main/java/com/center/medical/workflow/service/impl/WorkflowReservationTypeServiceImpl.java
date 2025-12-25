package com.center.medical.workflow.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.service.PeispatientService;
import com.center.medical.workflow.bean.dto.SubmitForApprovalDto;
import com.center.medical.workflow.bean.enums.WorkflowType;
import com.center.medical.workflow.bean.model.Workflow;
import com.center.medical.workflow.bean.model.WorkflowCase;
import com.center.medical.workflow.bean.model.WorkflowReservationType;
import com.center.medical.workflow.bean.param.SubmitForApprovalParam;
import com.center.medical.workflow.bean.vo.ToBeSubmittedVo;
import com.center.medical.workflow.dao.WorkflowReservationTypeMapper;
import com.center.medical.workflow.service.WorkflowCaseService;
import com.center.medical.workflow.service.WorkflowReservationTypeService;
import com.center.medical.workflow.service.WorkflowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 工作流-预约会员类型(WorkflowReservationType)服务实现类
 *
 * @author ay
 * @since 2024-03-07 13:36:10
 */
@Slf4j
@Service("workflowReservationTypeService")
@RequiredArgsConstructor
public class WorkflowReservationTypeServiceImpl extends ServiceImpl<WorkflowReservationTypeMapper, WorkflowReservationType> implements WorkflowReservationTypeService {

    private final WorkflowReservationTypeMapper workflowReservationTypeMapper;
    private final PeispatientService peispatientService;
    private final WorkflowService workflowService;
    private final WorkflowCaseService workflowCaseService;

    /**
     * 分页查询[工作流-预约会员类型]列表
     *
     * @param page  分页参数
     * @param param WorkflowReservationType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<WorkflowReservationType> getPage(PageParam<WorkflowReservationType> page, WorkflowReservationType param) {
        return workflowReservationTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public WorkflowReservationType getInfoById(String id) {
        return workflowReservationTypeMapper.getInfoById(id);
    }

    /**
     * 查询待提交的审批
     * @param orderId
     * @return
     */
    @Override
    public List<ToBeSubmittedVo> getToBeSubmitted(String orderId,String groupId) {
        return workflowReservationTypeMapper.getToBeSubmitted(orderId,groupId);
    }


    /**
     * 校正会员类型
     * @param orderId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean correctMembershipType(String orderId,String groupId) {
        List<ToBeSubmittedVo> list = workflowReservationTypeMapper.getToBeSubmitted(orderId,groupId);
        for (ToBeSubmittedVo vo : list) {
            Peispatient peispatient = peispatientService.getByPatientCode(vo.getPatientcode());
            Double amount = vo.getMoneyamount();
            //小于800的是普通会员，800到1500是vip，1500以上的是vvip
            if (amount < 800) {
                peispatient.setIdPatientclass("1");
            } else if (amount >= 800 && amount < 1500) {
                peispatient.setIdPatientclass("2");
            } else if (amount >= 1500) {
                peispatient.setIdPatientclass("3");
            }
            peispatientService.updateById(peispatient);

            //不提交的直接删除
            workflowReservationTypeMapper.deleteById(vo.getId());

        }
        return Boolean.TRUE;
    }

    /**
     * 提交审批
     * @param param
     * @return
     */
    @Override
    public Boolean submitForApproval(SubmitForApprovalParam param) {
        List<SubmitForApprovalDto> patientList = param.getPatientList();
        if (CollectionUtil.isEmpty(patientList)){
            throw new ServiceException("没有需要提交审批的数据");
        }
        //判断是否开启审批流
        Workflow workflow = workflowService.isOpen(SecurityUtils.getCId(), WorkflowType.OVER_RESERVATION.getCode());
        if (Objects.nonNull(workflow)) {
//            //同天同订单预约VIP、贵宾人数不能超过5个人
//            Integer count = workflowReservationTypeMapper.getTodayCount(param.getOrderId());
//            if (count+patientList.size() > 5){
//                throw new ServiceException("同天预约的VIP、贵宾人数不能超过5个人!");
//            }

            //保存工作流实例
            WorkflowCase workflowCase = new WorkflowCase();
            workflowCase.setCaseName(param.getOrderName()+"会员类型审核");
            workflowCase.setBizId(param.getOrderId());
            workflowCase.setFlowId(workflow.getId());
            workflowCase.setFzxid(SecurityUtils.getCId());
            workflowCase.setTypeFlag(workflow.getTypeFlag());
            workflowCase.setStatus(1);//进行中
            workflowCase.setRemark(param.getRemark());
            workflowCaseService.saOrUp(workflowCase);

            //保存体检者数据
            for (SubmitForApprovalDto dto : patientList) {
                WorkflowReservationType workflowReservationType = new WorkflowReservationType();
                workflowReservationType.setId(dto.getId());
                workflowReservationType.setMoneyamount(dto.getMoneyamount());
                workflowReservationType.setCaseId(workflowCase.getId());
                workflowReservationTypeMapper.updateById(workflowReservationType);
            }

        } else {
            throw new ServiceException("审批流未开启，请先去开启审批流!");
        }
        return Boolean.TRUE;
    }


    /**
     * 根据实例ID获取审批用户
     * @param caseId
     * @return
     */
    @Override
    public List<ToBeSubmittedVo> getListByCaseId(String caseId) {
        return workflowReservationTypeMapper.getListByCaseId(caseId);
    }
}

