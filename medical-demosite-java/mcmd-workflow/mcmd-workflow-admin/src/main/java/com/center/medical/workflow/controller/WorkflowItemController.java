package com.center.medical.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.workflow.bean.model.WorkflowItem;
import com.center.medical.workflow.service.WorkflowItemService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工作流节点(WorkflowItem)接口
 *
 * @author makejava
 * @since 2023-11-08 16:58:38
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "03工作流节点")
@RequestMapping("workflow/workflowItem")
public class WorkflowItemController extends BaseController {
    /**
     * 服务对象
     */
    private final WorkflowItemService workflowItemService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param workflowItem    查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询工作流节点")
    public R<IPage<WorkflowItem>> getPage(PageParamSimple pageParamSimple, WorkflowItem workflowItem) {
        PageParam<WorkflowItem> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.workflowItemService.getPage(page, workflowItem));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param itemId 主键
     * @return 单条数据
     */
    @GetMapping("{itemId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据itemId查工作流节点详情")
    @ApiImplicitParam(name = "itemId", value = "要查询的对象的主键{itemId}")
    public R<WorkflowItem> selectOne(@PathVariable String itemId) {
        return R.ok(this.workflowItemService.getInfoById(itemId));
    }

    /**
     * 新增数据
     *
     * @param workflowItem 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加工作流节点")
    @Log(title = "工作流节点", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"workflowItem.itemId"})
    public R insert(@RequestBody WorkflowItem workflowItem) {
        return R.toResult(this.workflowItemService.save(workflowItem));
    }

    /**
     * 修改数据
     *
     * @param workflowItem 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新工作流节点")
    @Log(title = "工作流节点", businessType = BusinessType.UPDATE)
    public R update(@RequestBody WorkflowItem workflowItem) {
        return R.toResult(this.workflowItemService.updateById(workflowItem));
    }

    /**
     * 删除数据
     *
     * @param itemIds 删除的对象主键{itemId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除工作流节点")
    @Log(title = "工作流节点", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{itemId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> itemIds) {
        return R.toResult(this.workflowItemService.removeByIds(itemIds));
    }
}


