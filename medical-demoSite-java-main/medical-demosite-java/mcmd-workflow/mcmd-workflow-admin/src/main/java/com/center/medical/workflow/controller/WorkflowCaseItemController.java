package com.center.medical.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.workflow.bean.model.WorkflowCaseItem;
import com.center.medical.workflow.service.WorkflowCaseItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 实例节点(WorkflowCaseItem)接口
 *
 * @author makejava
 * @since 2023-11-08 16:58:38
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "05实例节点")
@RequestMapping("workflow/workflowCaseItem")
public class WorkflowCaseItemController extends BaseController {
    /**
     * 服务对象
     */
    private final WorkflowCaseItemService workflowCaseItemService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple  分页参数
     * @param workflowCaseItem 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询实例节点")
    public R<IPage<WorkflowCaseItem>> getPage(PageParamSimple pageParamSimple, WorkflowCaseItem workflowCaseItem) {
        PageParam<WorkflowCaseItem> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.workflowCaseItemService.getPage(page, workflowCaseItem));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param itemId 主键
     * @return 单条数据
     */
    @GetMapping("{itemId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据itemId查实例节点详情")
    @ApiImplicitParam(name = "itemId", value = "要查询的对象的主键{itemId}")
    public R<WorkflowCaseItem> selectOne(@PathVariable String itemId) {
        return R.ok(this.workflowCaseItemService.getInfoById(itemId));
    }
}


