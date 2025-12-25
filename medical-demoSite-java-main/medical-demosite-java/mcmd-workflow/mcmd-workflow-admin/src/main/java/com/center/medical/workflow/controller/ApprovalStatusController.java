package com.center.medical.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.workflow.bean.param.ApprovalStatusParam;
import com.center.medical.workflow.bean.vo.ApprovalStatusVo;
import com.center.medical.workflow.service.ApprovalStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 我的审批情况(Peispatient)接口
 *
 * @author ay
 * @since 2024-03-29 13:37:41
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "我的审批情况")
@RequestMapping("workflow/approvalStatus")
public class ApprovalStatusController extends BaseController {
    /**
     * 服务对象
     */
    private final ApprovalStatusService approvalStatusService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询，就只有分页显示的不一样，后面的跟审批任务是一样的，详情也是用的一样的")
    public R<IPage<ApprovalStatusVo>> getPage(PageParamSimple pageParamSimple, ApprovalStatusParam param) {
        PageParam<ApprovalStatusVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.approvalStatusService.getPage(page, param));
    }





}
