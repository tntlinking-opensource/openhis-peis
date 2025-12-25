package com.center.medical.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.workflow.bean.model.Workflow;
import com.center.medical.workflow.bean.param.WorkflowParam;
import com.center.medical.workflow.bean.vo.WorkflowVo;
import com.center.medical.workflow.service.WorkflowService;
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
import java.util.Objects;

/**
 * 工作流(Workflow)接口
 *
 * @author makejava
 * @since 2023-11-08 16:58:38
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "02工作流")
@RequestMapping("workflow/workflow")
public class WorkflowController extends BaseController {
    /**
     * 服务对象
     */
    private final WorkflowService workflowService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询工作流")
    public R<IPage<WorkflowVo>> getPage(PageParamSimple pageParamSimple, WorkflowParam param) {
        PageParam<Workflow> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.workflowService.getPage(page, param));
    }


    /**
     * 判断是否启动工作流
     *
     * @param fzxid    分中心ID
     * @param typeFlag 类型标识
     * @return
     */
    @GetMapping("/isOpen")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "判断是否启动工作流", notes = "判断是否启动工作流")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fzxid", value = "分中心ID"),
            @ApiImplicitParam(name = "typeFlag", value = "类型标识")
    })
    public R<Boolean> isOpen(@RequestParam String fzxid, @RequestParam String typeFlag) {
        Workflow workflow = this.workflowService.isOpen(fzxid, typeFlag);
        if (Objects.nonNull(workflow)) {
            return R.ok(Boolean.TRUE);
        }
        return R.ok(Boolean.FALSE);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查工作流详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Workflow> selectOne(@PathVariable String id) {
        return R.ok(this.workflowService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param workflow 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加工作流")
    @Log(title = "工作流", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"workflow.id"})
    public R insert(@RequestBody Workflow workflow) {
        return R.toResult(this.workflowService.saOrUp(workflow));
    }

    /**
     * 修改数据
     *
     * @param workflow 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新工作流")
    @Log(title = "工作流", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Workflow workflow) {
        return R.toResult(this.workflowService.saOrUp(workflow));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除工作流")
    @Log(title = "工作流", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.workflowService.delByIds(ids));
    }
}


