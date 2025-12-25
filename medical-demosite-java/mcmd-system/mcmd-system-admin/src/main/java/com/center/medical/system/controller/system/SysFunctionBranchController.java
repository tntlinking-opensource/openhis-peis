package com.center.medical.system.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.system.bean.model.SysFunctionBranch;
import com.center.medical.system.service.SysFunctionBranchService;
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
 * 业务功能-分中心关联(SysFunctionBranch)接口
 *
 * @author makejava
 * @since 2024-03-19 11:13:48
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "业务功能-分中心关联")
@RequestMapping("sysFunctionBranch")
public class SysFunctionBranchController extends BaseController {
    /**
     * 服务对象
     */
    private final SysFunctionBranchService sysFunctionBranchService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple   分页参数
     * @param sysFunctionBranch 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询业务功能-分中心关联")
    public R<IPage<SysFunctionBranch>> getPage(PageParamSimple pageParamSimple, SysFunctionBranch sysFunctionBranch) {
        PageParam<SysFunctionBranch> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysFunctionBranchService.getPage(page, sysFunctionBranch));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查业务功能-分中心关联详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<SysFunctionBranch> selectOne(@PathVariable String id) {
        return R.ok(this.sysFunctionBranchService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param sysFunctionBranch 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加业务功能-分中心关联")
    @Log(title = "业务功能-分中心关联", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysFunctionBranch.id"})
    public R insert(@RequestBody SysFunctionBranch sysFunctionBranch) {
        return R.toResult(this.sysFunctionBranchService.save(sysFunctionBranch));
    }

    /**
     * 修改数据
     *
     * @param sysFunctionBranch 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新业务功能-分中心关联")
    @Log(title = "业务功能-分中心关联", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysFunctionBranch sysFunctionBranch) {
        return R.toResult(this.sysFunctionBranchService.updateById(sysFunctionBranch));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除业务功能-分中心关联")
    @Log(title = "业务功能-分中心关联", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.sysFunctionBranchService.removeByIds(ids));
    }
}


