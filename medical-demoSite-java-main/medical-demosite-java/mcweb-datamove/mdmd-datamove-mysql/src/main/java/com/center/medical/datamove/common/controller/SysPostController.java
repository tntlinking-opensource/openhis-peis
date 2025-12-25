package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.SysPost;
import com.center.medical.datamove.common.service.SysPostService;
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
 * 岗位信息表(SysPost)接口
 *
 * @author ay
 * @since 2023-07-17 20:54:36
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "岗位信息表")
@RequestMapping("sysPost")
public class SysPostController extends BaseController {
    /**
     * 服务对象
     */
    private final SysPostService sysPostService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param sysPost         查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询岗位信息表")
    public R<IPage<SysPost>> getPage(PageParamSimple pageParamSimple, SysPost sysPost) {
        PageParam<SysPost> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysPostService.getPage(page, sysPost));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param postId 主键
     * @return 单条数据
     */
    @GetMapping("{postId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据postId查岗位信息表详情")
    @ApiImplicitParam(name = "postId", value = "要查询的对象的主键{postId}")
    public R<SysPost> selectOne(@PathVariable Long postId) {
        return R.ok(this.sysPostService.getInfoById(postId));
    }

    /**
     * 新增数据
     *
     * @param sysPost 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加岗位信息表")
    @Log(title = "岗位信息表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysPost.postId"})
    public R insert(@RequestBody SysPost sysPost) {
        return R.toResult(this.sysPostService.save(sysPost));
    }

    /**
     * 修改数据
     *
     * @param sysPost 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新岗位信息表")
    @Log(title = "岗位信息表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysPost sysPost) {
        return R.toResult(this.sysPostService.updateById(sysPost));
    }

    /**
     * 删除数据
     *
     * @param postIds 删除的对象主键{postId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除岗位信息表")
    @Log(title = "岗位信息表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{postId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> postIds) {
        return R.toResult(this.sysPostService.removeByIds(postIds));
    }
}

