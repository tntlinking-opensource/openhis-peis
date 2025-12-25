package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.WsUserRole;
import com.center.medical.datamove.common.service.WsUserRoleService;
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
 * 网站用户权限(WsUserRole)接口
 *
 * @author ay
 * @since 2023-07-17 20:54:42
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "网站用户权限")
@RequestMapping("wsUserRole")
public class WsUserRoleController extends BaseController {
    /**
     * 服务对象
     */
    private final WsUserRoleService wsUserRoleService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param wsUserRole      查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询网站用户权限")
    public R<IPage<WsUserRole>> getPage(PageParamSimple pageParamSimple, WsUserRole wsUserRole) {
        PageParam<WsUserRole> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.wsUserRoleService.getPage(page, wsUserRole));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param userId 主键
     * @return 单条数据
     */
    @GetMapping("{userId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据userId查网站用户权限详情")
    @ApiImplicitParam(name = "userId", value = "要查询的对象的主键{userId}")
    public R<WsUserRole> selectOne(@PathVariable String userId) {
        return R.ok(this.wsUserRoleService.getInfoById(userId));
    }

    /**
     * 新增数据
     *
     * @param wsUserRole 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加网站用户权限")
    @Log(title = "网站用户权限", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"wsUserRole.userId"})
    public R insert(@RequestBody WsUserRole wsUserRole) {
        return R.toResult(this.wsUserRoleService.save(wsUserRole));
    }

    /**
     * 修改数据
     *
     * @param wsUserRole 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新网站用户权限")
    @Log(title = "网站用户权限", businessType = BusinessType.UPDATE)
    public R update(@RequestBody WsUserRole wsUserRole) {
        return R.toResult(this.wsUserRoleService.updateById(wsUserRole));
    }

    /**
     * 删除数据
     *
     * @param userIds 删除的对象主键{userId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除网站用户权限")
    @Log(title = "网站用户权限", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{userId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> userIds) {
        return R.toResult(this.wsUserRoleService.removeByIds(userIds));
    }
}

