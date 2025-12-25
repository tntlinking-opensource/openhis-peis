package com.center.medical.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.workflow.bean.model.WorkflowCase;
import com.center.medical.workflow.bean.param.UpdateCaseItemParam;
import com.center.medical.workflow.bean.param.WorkflowCaseParam;
import com.center.medical.workflow.service.WorkflowCaseService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 工作流实例记录(WorkflowCase)接口
 *
 * @author makejava
 * @since 2023-11-08 16:58:38
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "04实例记录")
@RequestMapping("workflow/workflowCase")
public class WorkflowCaseController extends BaseController {
    /**
     * 服务对象
     */
    private final WorkflowCaseService workflowCaseService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询工作流实例记录")
    public R<IPage<WorkflowCase>> getPage(PageParamSimple pageParamSimple, WorkflowCaseParam param) {
        PageParam<WorkflowCase> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.workflowCaseService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查工作流实例记录详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<WorkflowCase> selectOne(@PathVariable String id) {
        return R.ok(this.workflowCaseService.getInfoById(id));
    }


    /**
     * 根据业务ID获取实例详情
     *
     * @param bizId    业务ID
     * @param typeFlag 类型标识
     * @return 单条数据
     */
    @GetMapping("getInfoByBzId/{bizId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "根据业务ID获取实例详情", notes = "根据业务ID获取实例详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bizId", value = "要查询的对象的业务ID"),
            @ApiImplicitParam(name = "typeFlag", value = "类型标识")
    })
    public R<WorkflowCase> getInfoByBzId(@PathVariable String bizId, String typeFlag) {
        return R.ok(this.workflowCaseService.getInfoByBzId(bizId, typeFlag));
    }

    /**
     * 新增数据
     *
     * @param workflowCase 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加工作流实例记录")
    @Log(title = "工作流实例-添加", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"workflowCase.id"})
    public R insert(@RequestBody WorkflowCase workflowCase) {
        return R.toResult(this.workflowCaseService.saOrUp(workflowCase));
    }

    /**
     * 修改数据
     *
     * @param workflowCase 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新工作流实例记录")
    @Log(title = "工作流实例-更新", businessType = BusinessType.UPDATE)
    public R update(@RequestBody WorkflowCase workflowCase) {
        return R.toResult(this.workflowCaseService.saOrUp(workflowCase));
    }

    /**
     * 更新实例节点状态
     *
     * @param param 实例节点更新信息
     * @return 修改结果
     */
    @PutMapping("/updateItemStatus")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新节点状态", notes = "更新实例节点状态")
    @Log(title = "工作流实例-更新节点状态", businessType = BusinessType.UPDATE)
    public R updateItemStatus(@RequestBody UpdateCaseItemParam param) {
        return R.toResult(this.workflowCaseService.updateItemStatus(param));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除工作流实例记录")
    @Log(title = "工作流实例-删除", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.workflowCaseService.removeByIds(ids));
    }
}


