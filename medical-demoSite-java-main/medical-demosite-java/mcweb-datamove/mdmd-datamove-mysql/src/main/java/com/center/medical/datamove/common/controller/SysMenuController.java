package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.SysMenu;
import com.center.medical.datamove.common.service.SysMenuService;
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
 * 菜单权限表(SysMenu)接口
 *
 * @author ay
 * @since 2023-07-17 20:54:36
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "菜单权限表")
@RequestMapping("sysMenu")
public class SysMenuController extends BaseController {
    /**
     * 服务对象
     */
    private final SysMenuService sysMenuService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param sysMenu         查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询菜单权限表")
    public R<IPage<SysMenu>> getPage(PageParamSimple pageParamSimple, SysMenu sysMenu) {
        PageParam<SysMenu> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysMenuService.getPage(page, sysMenu));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param menuId 主键
     * @return 单条数据
     */
    @GetMapping("{menuId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据menuId查菜单权限表详情")
    @ApiImplicitParam(name = "menuId", value = "要查询的对象的主键{menuId}")
    public R<SysMenu> selectOne(@PathVariable Long menuId) {
        return R.ok(this.sysMenuService.getInfoById(menuId));
    }

    /**
     * 新增数据
     *
     * @param sysMenu 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加菜单权限表")
    @Log(title = "菜单权限表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysMenu.menuId"})
    public R insert(@RequestBody SysMenu sysMenu) {
        return R.toResult(this.sysMenuService.save(sysMenu));
    }

    /**
     * 修改数据
     *
     * @param sysMenu 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新菜单权限表")
    @Log(title = "菜单权限表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysMenu sysMenu) {
        return R.toResult(this.sysMenuService.updateById(sysMenu));
    }

    /**
     * 删除数据
     *
     * @param menuIds 删除的对象主键{menuId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除菜单权限表")
    @Log(title = "菜单权限表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{menuId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> menuIds) {
        return R.toResult(this.sysMenuService.removeByIds(menuIds));
    }
}

