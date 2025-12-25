package com.center.medical.system.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.system.bean.model.SysDepartment;
import com.center.medical.system.service.SysDepartmentService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 沃德医疗部门总集(所有中心部门的总集)(SysDepartment)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-18 19:26:34
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "沃德医疗部门总集(所有中心部门的总集)")
@RequestMapping("system/department")
public class SysDepartmentController extends BaseController {
    /**
     * 服务对象
     */
    private final SysDepartmentService sysDepartmentService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param sysDepartment   查询实体
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询沃德医疗部门总集(所有中心部门的总集)")
    public R<IPage<SysDepartment>> getPage(PageParamSimple pageParamSimple, SysDepartment sysDepartment) {
        PageParam<SysDepartment> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysDepartmentService.getPage(page, sysDepartment));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查沃德医疗部门总集(所有中心部门的总集)详情")
    public R<SysDepartment> selectOne(@PathVariable String id) {
        return R.ok(this.sysDepartmentService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param sysDepartment 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加沃德医疗部门总集(所有中心部门的总集)")
    @Log(title = "沃德医疗部门总集(所有中心部门的总集)", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysDepartment.id"})
    public R insert(@RequestBody SysDepartment sysDepartment) {
        return R.toResult(this.sysDepartmentService.save(sysDepartment));
    }

    /**
     * 修改数据
     *
     * @param sysDepartment 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新沃德医疗部门总集(所有中心部门的总集)")
    @Log(title = "沃德医疗部门总集(所有中心部门的总集)", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysDepartment sysDepartment) {
        return R.toResult(this.sysDepartmentService.updateById(sysDepartment));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除沃德医疗部门总集(所有中心部门的总集)")
    @Log(title = "沃德医疗部门总集(所有中心部门的总集)", businessType = BusinessType.DELETE)
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.sysDepartmentService.removeByIds(ids));
    }
}

