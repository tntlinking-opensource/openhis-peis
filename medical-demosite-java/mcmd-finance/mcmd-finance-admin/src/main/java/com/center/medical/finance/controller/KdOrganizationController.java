package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.finance.bean.model.KdOrganization;
import com.center.medical.finance.service.KdOrganizationService;
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
 * kingdeeorganization(KdOrganization)表控制层
 *
 * @author 路飞船长
 * @since 2023-02-17 09:34:28
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "kingdeeorganization")
@RequestMapping("kdOrganization")
public class KdOrganizationController extends BaseController {
    /**
     * 服务对象
     */
    private final KdOrganizationService kdOrganizationService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param kdOrganization  查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询kingdeeorganization")
    public R<IPage<KdOrganization>> getPage(PageParamSimple pageParamSimple, KdOrganization kdOrganization) {
        PageParam<KdOrganization> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.kdOrganizationService.getPage(page, kdOrganization));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param orgId 主键
     * @return 单条数据
     */
    @GetMapping("{orgId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据orgId查kingdeeorganization详情")
    @ApiImplicitParam(name = "orgId", value = "要查询的对象的主键{orgId}")
    public R<KdOrganization> selectOne(@PathVariable String orgId) {
        return R.ok(this.kdOrganizationService.getInfoById(orgId));
    }

    /**
     * 新增数据
     *
     * @param kdOrganization 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加kingdeeorganization")
    @Log(title = "kingdeeorganization", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"kdOrganization.orgId"})
    public R insert(@RequestBody KdOrganization kdOrganization) {
        return R.toResult(this.kdOrganizationService.save(kdOrganization));
    }

    /**
     * 修改数据
     *
     * @param kdOrganization 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新kingdeeorganization")
    @Log(title = "kingdeeorganization", businessType = BusinessType.UPDATE)
    public R update(@RequestBody KdOrganization kdOrganization) {
        return R.toResult(this.kdOrganizationService.updateById(kdOrganization));
    }

    /**
     * 删除数据
     *
     * @param orgIds 删除的对象主键{orgId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除kingdeeorganization")
    @Log(title = "kingdeeorganization", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{orgId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> orgIds) {
        return R.toResult(this.kdOrganizationService.removeByIds(orgIds));
    }
}

