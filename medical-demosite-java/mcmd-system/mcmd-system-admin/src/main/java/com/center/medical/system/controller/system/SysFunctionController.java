package com.center.medical.system.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.system.bean.model.SysFunction;
import com.center.medical.system.service.SysFunctionService;
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
 * 系统业务功能(SysFunction)接口
 *
 * @author makejava
 * @since 2024-03-19 11:13:48
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "系统业务功能")
@RequestMapping("sysFunction")
public class SysFunctionController extends BaseController {
    /**
     * 服务对象
     */
    private final SysFunctionService sysFunctionService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param sysFunction     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询系统业务功能")
    public R<IPage<SysFunction>> getPage(PageParamSimple pageParamSimple, SysFunction sysFunction) {
        PageParam<SysFunction> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysFunctionService.getPage(page, sysFunction));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param functionId 主键
     * @return 单条数据
     */
    @GetMapping("{functionId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据functionId查系统业务功能详情")
    @ApiImplicitParam(name = "functionId", value = "要查询的对象的主键{functionId}")
    public R<SysFunction> selectOne(@PathVariable String functionId) {
        return R.ok(this.sysFunctionService.getInfoById(functionId));
    }

    /**
     * 新增数据
     *
     * @param sysFunction 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加系统业务功能")
    @Log(title = "系统业务功能", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysFunction.functionId"})
    public R insert(@RequestBody SysFunction sysFunction) {
        return R.toResult(this.sysFunctionService.save(sysFunction));
    }

    /**
     * 修改数据
     *
     * @param sysFunction 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新系统业务功能")
    @Log(title = "系统业务功能", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysFunction sysFunction) {
        return R.toResult(this.sysFunctionService.updateById(sysFunction));
    }

    /**
     * 删除数据
     *
     * @param functionIds 删除的对象主键{functionId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除系统业务功能")
    @Log(title = "系统业务功能", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{functionId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> functionIds) {
        return R.toResult(this.sysFunctionService.removeByIds(functionIds));
    }
}


