package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.MdUser;
import com.center.medical.datamove.common.service.MdUserService;
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
 * 体检用户账号(MdUser)接口
 *
 * @author ay
 * @since 2023-07-17 20:49:15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检用户账号")
@RequestMapping("mdUser")
public class MdUserController extends BaseController {
    /**
     * 服务对象
     */
    private final MdUserService mdUserService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param mdUser          查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询体检用户账号")
    public R<IPage<MdUser>> getPage(PageParamSimple pageParamSimple, MdUser mdUser) {
        PageParam<MdUser> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.mdUserService.getPage(page, mdUser));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param userId 主键
     * @return 单条数据
     */
    @GetMapping("{userId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据userId查体检用户账号详情")
    @ApiImplicitParam(name = "userId", value = "要查询的对象的主键{userId}")
    public R<MdUser> selectOne(@PathVariable String userId) {
        return R.ok(this.mdUserService.getInfoById(userId));
    }

    /**
     * 新增数据
     *
     * @param mdUser 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加体检用户账号")
    @Log(title = "体检用户账号", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"mdUser.userId"})
    public R insert(@RequestBody MdUser mdUser) {
        return R.toResult(this.mdUserService.save(mdUser));
    }

    /**
     * 修改数据
     *
     * @param mdUser 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新体检用户账号")
    @Log(title = "体检用户账号", businessType = BusinessType.UPDATE)
    public R update(@RequestBody MdUser mdUser) {
        return R.toResult(this.mdUserService.updateById(mdUser));
    }

    /**
     * 删除数据
     *
     * @param userIds 删除的对象主键{userId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除体检用户账号")
    @Log(title = "体检用户账号", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{userId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> userIds) {
        return R.toResult(this.mdUserService.removeByIds(userIds));
    }
}

