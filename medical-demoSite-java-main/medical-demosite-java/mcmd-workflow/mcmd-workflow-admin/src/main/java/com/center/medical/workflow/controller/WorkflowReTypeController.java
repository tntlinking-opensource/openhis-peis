package com.center.medical.workflow.controller;

import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.workflow.bean.param.SubmitForApprovalParam;
import com.center.medical.workflow.bean.vo.ToBeSubmittedVo;
import com.center.medical.workflow.service.WorkflowCaseService;
import com.center.medical.workflow.service.WorkflowReservationTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工作流-预约会员类型(WorkflowReservationType)接口
 *
 * @author ay
 * @since 2024-03-07 13:36:10
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "会员类型审批")
@RequestMapping("workflow/workflowReType")
public class WorkflowReTypeController extends BaseController {
    /**
     * 服务对象
     */
    private final WorkflowReservationTypeService workflowReservationTypeService;
    private final MapperFacade mapperFacade;
    private final WorkflowCaseService workflowCaseService;



    /**
     * 查询待提交的审批
     * @param orderId
     * @return
     */
    @GetMapping("/getToBeSubmitted")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询待提交的审批", notes = "查询待提交的审批")
    @ApiImplicitParam(name = "orderId", value = "订单id")
    public R<List<ToBeSubmittedVo>> getToBeSubmitted(String orderId,String groupId) {
        List<ToBeSubmittedVo> list = workflowReservationTypeService.getToBeSubmitted(orderId,groupId);
        return R.ok(list);
    }




    /**
     * 查询待提交的审批
     * @param param
     * @return
     */
    @PostMapping("/submitForApproval")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "提交审批", notes = "提交审批")
    public R submitForApproval(@RequestBody SubmitForApprovalParam param) {
        Boolean b = workflowReservationTypeService.submitForApproval(param);
        return R.ok(b);
    }




    /**
     * 查询待提交的审批
     * @param orderId
     * @return
     */
    @PostMapping("/correctMembershipType")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "校正会员类型", notes = "校正会员类型,目前不可以选择，要校正就整个订单下全部一起校正了")
    public R correctMembershipType(@RequestParam String orderId,@RequestParam(required = false) String groupId) {
        Boolean b = workflowReservationTypeService.correctMembershipType(orderId,groupId);
        return R.ok(b);
    }


}
