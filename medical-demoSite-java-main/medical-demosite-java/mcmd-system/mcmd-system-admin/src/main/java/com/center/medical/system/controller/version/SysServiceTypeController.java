package com.center.medical.system.controller.version;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.system.bean.model.SysServiceType;
import com.center.medical.system.service.SysServiceTypeService;
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
 * 系统服务(记录系统服务种类)(SysServiceType)接口
 *
 * @author makejava
 * @since 2024-03-01 18:03:42
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "系统服务(记录系统服务种类)")
@RequestMapping("sysServiceType")
public class SysServiceTypeController extends BaseController {
    /**
     * 服务对象
     */
    private final SysServiceTypeService sysServiceTypeService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param sysServiceType  查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询系统服务(记录系统服务种类)")
    public R<IPage<SysServiceType>> getPage(PageParamSimple pageParamSimple, SysServiceType sysServiceType) {
        PageParam<SysServiceType> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysServiceTypeService.getPage(page, sysServiceType));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param serviceId 主键
     * @return 单条数据
     */
    @GetMapping("{serviceId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据serviceId查系统服务(记录系统服务种类)详情")
    @ApiImplicitParam(name = "serviceId", value = "要查询的对象的主键{serviceId}")
    public R<SysServiceType> selectOne(@PathVariable Integer serviceId) {
        return R.ok(this.sysServiceTypeService.getInfoById(serviceId));
    }

    /**
     * 新增数据
     *
     * @param sysServiceType 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加系统服务(记录系统服务种类)")
    @Log(title = "系统服务(记录系统服务种类)", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysServiceType.serviceId"})
    public R insert(@RequestBody SysServiceType sysServiceType) {
        return R.toResult(this.sysServiceTypeService.save(sysServiceType));
    }

    /**
     * 修改数据
     *
     * @param sysServiceType 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新系统服务(记录系统服务种类)")
    @Log(title = "系统服务(记录系统服务种类)", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysServiceType sysServiceType) {
        return R.toResult(this.sysServiceTypeService.updateById(sysServiceType));
    }

    /**
     * 删除数据
     *
     * @param serviceIds 删除的对象主键{serviceId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除系统服务(记录系统服务种类)")
    @Log(title = "系统服务(记录系统服务种类)", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{serviceId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Integer> serviceIds) {
        return R.toResult(this.sysServiceTypeService.removeByIds(serviceIds));
    }
}


