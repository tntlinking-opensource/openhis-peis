package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.SysUserRole;
import com.center.medical.datamove.common.service.SysUserRoleService;
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
 * 用户和角色关联表(SysUserRole)接口
 *
 * @author ay
 * @since 2023-07-17 20:54:40
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "用户和角色关联表")
@RequestMapping("sysUserRole")
public class SysUserRoleController extends BaseController {
    /**
     * 服务对象
     */
    private final SysUserRoleService sysUserRoleService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param sysUserRole     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询用户和角色关联表")
    public R<IPage<SysUserRole>> getPage(PageParamSimple pageParamSimple, SysUserRole sysUserRole) {
        PageParam<SysUserRole> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysUserRoleService.getPage(page, sysUserRole));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param userId 主键
     * @return 单条数据
     */
    @GetMapping("{userId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据userId查用户和角色关联表详情")
    @ApiImplicitParam(name = "userId", value = "要查询的对象的主键{userId}")
    public R<SysUserRole> selectOne(@PathVariable Long userId) {
        return R.ok(this.sysUserRoleService.getInfoById(userId));
    }

    /**
     * 新增数据
     *
     * @param sysUserRole 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加用户和角色关联表")
    @Log(title = "用户和角色关联表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysUserRole.userId"})
    public R insert(@RequestBody SysUserRole sysUserRole) {
        return R.toResult(this.sysUserRoleService.save(sysUserRole));
    }

    /**
     * 修改数据
     *
     * @param sysUserRole 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新用户和角色关联表")
    @Log(title = "用户和角色关联表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysUserRole sysUserRole) {
        return R.toResult(this.sysUserRoleService.updateById(sysUserRole));
    }

    /**
     * 删除数据
     *
     * @param userIds 删除的对象主键{userId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除用户和角色关联表")
    @Log(title = "用户和角色关联表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{userId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> userIds) {
        return R.toResult(this.sysUserRoleService.removeByIds(userIds));
    }
}

