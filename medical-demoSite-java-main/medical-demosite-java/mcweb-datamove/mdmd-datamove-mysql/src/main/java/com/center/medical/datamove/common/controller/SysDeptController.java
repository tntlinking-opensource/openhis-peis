package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.SysDept;
import com.center.medical.datamove.common.service.SysDeptService;
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
 * 沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）(SysDept)接口
 *
 * @author ay
 * @since 2023-07-17 20:54:34
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）")
@RequestMapping("sysDept")
public class SysDeptController extends BaseController {
    /**
     * 服务对象
     */
    private final SysDeptService sysDeptService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param sysDept         查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）")
    public R<IPage<SysDept>> getPage(PageParamSimple pageParamSimple, SysDept sysDept) {
        PageParam<SysDept> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysDeptService.getPage(page, sysDept));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param deptId 主键
     * @return 单条数据
     */
    @GetMapping("{deptId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据deptId查沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）详情")
    @ApiImplicitParam(name = "deptId", value = "要查询的对象的主键{deptId}")
    public R<SysDept> selectOne(@PathVariable Long deptId) {
        return R.ok(this.sysDeptService.getInfoById(deptId));
    }

    /**
     * 新增数据
     *
     * @param sysDept 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）")
    @Log(title = "沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysDept.deptId"})
    public R insert(@RequestBody SysDept sysDept) {
        return R.toResult(this.sysDeptService.save(sysDept));
    }

    /**
     * 修改数据
     *
     * @param sysDept 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）")
    @Log(title = "沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysDept sysDept) {
        return R.toResult(this.sysDeptService.updateById(sysDept));
    }

    /**
     * 删除数据
     *
     * @param deptIds 删除的对象主键{deptId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）")
    @Log(title = "沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{deptId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> deptIds) {
        return R.toResult(this.sysDeptService.removeByIds(deptIds));
    }
}

