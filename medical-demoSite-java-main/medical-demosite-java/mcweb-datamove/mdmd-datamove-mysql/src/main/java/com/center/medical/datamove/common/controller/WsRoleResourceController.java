package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.WsRoleResource;
import com.center.medical.datamove.common.service.WsRoleResourceService;
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
 * 角色-资源映射表(WsRoleResource)接口
 *
 * @author ay
 * @since 2023-07-17 20:54:41
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "角色-资源映射表")
@RequestMapping("wsRoleResource")
public class WsRoleResourceController extends BaseController {
    /**
     * 服务对象
     */
    private final WsRoleResourceService wsRoleResourceService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param wsRoleResource  查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询角色-资源映射表")
    public R<IPage<WsRoleResource>> getPage(PageParamSimple pageParamSimple, WsRoleResource wsRoleResource) {
        PageParam<WsRoleResource> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.wsRoleResourceService.getPage(page, wsRoleResource));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param roleId 主键
     * @return 单条数据
     */
    @GetMapping("{roleId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据roleId查角色-资源映射表详情")
    @ApiImplicitParam(name = "roleId", value = "要查询的对象的主键{roleId}")
    public R<WsRoleResource> selectOne(@PathVariable String roleId) {
        return R.ok(this.wsRoleResourceService.getInfoById(roleId));
    }

    /**
     * 新增数据
     *
     * @param wsRoleResource 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加角色-资源映射表")
    @Log(title = "角色-资源映射表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"wsRoleResource.roleId"})
    public R insert(@RequestBody WsRoleResource wsRoleResource) {
        return R.toResult(this.wsRoleResourceService.save(wsRoleResource));
    }

    /**
     * 修改数据
     *
     * @param wsRoleResource 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新角色-资源映射表")
    @Log(title = "角色-资源映射表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody WsRoleResource wsRoleResource) {
        return R.toResult(this.wsRoleResourceService.updateById(wsRoleResource));
    }

    /**
     * 删除数据
     *
     * @param roleIds 删除的对象主键{roleId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除角色-资源映射表")
    @Log(title = "角色-资源映射表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{roleId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> roleIds) {
        return R.toResult(this.wsRoleResourceService.removeByIds(roleIds));
    }
}

