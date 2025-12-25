package com.center.medical.workflow.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.workflow.bean.model.Workflow;
import com.center.medical.workflow.bean.model.WorkflowType;
import com.center.medical.workflow.bean.param.WorkflowTypeParam;
import com.center.medical.workflow.service.WorkflowService;
import com.center.medical.workflow.service.WorkflowTypeService;
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
 * 工作流类型(WorkflowType)接口
 *
 * @author makejava
 * @since 2023-11-08 16:58:39
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "01工作流类型")
@RequestMapping("workflow/workflowType")
public class WorkflowTypeController extends BaseController {
    /**
     * 服务对象
     */
    private final WorkflowTypeService workflowTypeService;
    private final WorkflowService workflowService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param workflowType    查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询工作流类型")
    public R<IPage<WorkflowType>> getPage(PageParamSimple pageParamSimple, WorkflowType workflowType) {
        PageParam<WorkflowType> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.workflowTypeService.getPage(page, workflowType));
    }


    /**
     * 查询所有数据
     *
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询所有数据", notes = "查询所有数据")
    public R<List<WorkflowType>> getList(WorkflowTypeParam param) {
        LambdaQueryWrapper<WorkflowType> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(param.getTypeFlag())) {
            wrapper.eq(WorkflowType::getTypeFlag, param.getTypeFlag());
        }
        if (StringUtils.isNotBlank(param.getTypeName())) {
            wrapper.like(WorkflowType::getTypeName, param.getTypeName());
        }
        return R.ok(this.workflowTypeService.list(wrapper));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param typeId 主键
     * @return 单条数据
     */
    @GetMapping("{typeId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据typeId查工作流类型详情")
    @ApiImplicitParam(name = "typeId", value = "要查询的对象的主键{typeId}")
    public R<WorkflowType> selectOne(@PathVariable Integer typeId) {
        return R.ok(this.workflowTypeService.getInfoById(typeId));
    }

    /**
     * 新增数据
     *
     * @param workflowType 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加工作流类型")
    @Log(title = "工作流类型", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"workflowType.typeId"})
    public R insert(@RequestBody WorkflowType workflowType) {
        return R.toResult(this.workflowTypeService.save(workflowType));
    }

    /**
     * 修改数据
     *
     * @param workflowType 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新工作流类型")
    @Log(title = "工作流类型", businessType = BusinessType.UPDATE)
    public R update(@RequestBody WorkflowType workflowType) {
        return R.toResult(this.workflowTypeService.updateById(workflowType));
    }

    /**
     * 删除数据
     *
     * @param typeFlags 删除的对象主键{typeId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除工作流类型")
    @Log(title = "工作流类型", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象typeFlag集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> typeFlags) {
        Boolean flag = Boolean.FALSE;
        String str = "";
        for (String typeFlag : typeFlags) {
            //查看是否该类型下是否有工作流
            if (workflowService.count(new LambdaQueryWrapper<Workflow>().eq(Workflow::getTypeFlag, typeFlag)) > 0) {
                flag = Boolean.TRUE;
                str = str + "[typeFlag]";
            }
        }
        if (flag) {
            throw new GlobalException("这些类型{" + str + "}下有工作流记录，无法删除！");
        }
        return R.toResult(this.workflowTypeService.remove(new LambdaQueryWrapper<WorkflowType>().in(WorkflowType::getTypeFlag, typeFlags)));
    }
}


