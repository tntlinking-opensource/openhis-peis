package com.center.medical.workflow.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.workflow.bean.model.WorkflowReservationType;
import com.center.medical.workflow.bean.vo.ToBeSubmittedVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工作流-预约会员类型(WorkflowReservationType)数据库访问层
 *
 * @author ay
 * @since 2024-03-07 13:36:10
 */
public interface WorkflowReservationTypeMapper extends BaseMapper<WorkflowReservationType> {

    /**
     * 分页查询[工作流-预约会员类型]列表
     *
     * @param page  分页参数
     * @param param WorkflowReservationType查询参数
     * @return 分页数据
     */
    IPage<WorkflowReservationType> getPage(PageParam<WorkflowReservationType> page, @Param("param") WorkflowReservationType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WorkflowReservationType getInfoById(@Param("id") String id);

    /**
     * 查询待提交的审批
     * @param orderId
     * @return
     */
    List<ToBeSubmittedVo> getToBeSubmitted(@Param("orderId") String orderId, @Param("groupId") String groupId);

    /**
     * 查询今天预约审批的人数
     * @param orderId
     * @return
     */
    Integer getTodayCount(@Param("orderId") String orderId);

    /**
     * 根据实例ID获取审批用户
     * @param caseId
     * @return
     */
    List<ToBeSubmittedVo> getListByCaseId(@Param("caseId") String caseId);
}
