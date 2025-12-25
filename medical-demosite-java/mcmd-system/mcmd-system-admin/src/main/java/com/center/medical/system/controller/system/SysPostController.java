package com.center.medical.system.controller.system;

import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.UserConstants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.AjaxResult;
import com.center.medical.common.core.domain.entity.SysPost;
import com.center.medical.common.core.page.TableDataInfo;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.system.service.ISysPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 岗位信息操作处理
 *
 * @author 路飞
 */
@Api(tags = "岗位信息")
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController {
    @Resource
    private ISysPostService postService;

    /**
     * 获取岗位列表
     */
    @PreAuthorize("@ss.hasPermi('system:post:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取岗位列表", notes = "获取岗位列表")
    public TableDataInfo list(SysPost post) {
        startPage();
        List<SysPost> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @Log(title = "岗位管理-导出", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:post:export')")
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出")
    public void export(HttpServletResponse response, SysPost post) {
        List<SysPost> list = postService.selectPostList(post);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        util.exportExcel(response, list, "岗位数据");
    }

    /**
     * 根据岗位编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:post:query')")
    @GetMapping(value = "/{postId}")
    @ApiOperation(value = "根据岗位编号获取详细信息", notes = "根据岗位编号获取详细信息")
    public AjaxResult getInfo(@PathVariable Long postId) {
        return AjaxResult.success(postService.selectPostById(postId));
    }

    /**
     * 新增岗位
     */
    @PreAuthorize("@ss.hasPermi('system:post:add')")
    @Log(title = "岗位管理-新增岗位", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增岗位", notes = "新增岗位")
    public AjaxResult add(@Validated @RequestBody SysPost post) {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return AjaxResult.error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return AjaxResult.error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setCreateBy(getUsername());
        return toAjax(postService.insertPost(post));
    }

    /**
     * 修改岗位
     */
    @PreAuthorize("@ss.hasPermi('system:post:edit')")
    @Log(title = "岗位管理-修改岗位", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改岗位", notes = "修改岗位")
    public AjaxResult edit(@Validated @RequestBody SysPost post) {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return AjaxResult.error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return AjaxResult.error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setUpdateBy(getUsername());
        return toAjax(postService.updatePost(post));
    }

    /**
     * 删除岗位
     */
    @PreAuthorize("@ss.hasPermi('system:post:remove')")
    @Log(title = "岗位管理-删除岗位", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    @ApiOperation(value = "删除岗位", notes = "删除岗位")
    public AjaxResult remove(@PathVariable Long[] postIds) {
        return toAjax(postService.deletePostByIds(postIds));
    }

    /**
     * 获取岗位选择框列表
     */
    @GetMapping("/optionselect")
    @ApiOperation(value = "获取岗位选择框列表", notes = "获取岗位选择框列表")
    public AjaxResult optionselect() {
        List<SysPost> posts = postService.selectPostAll();
        return AjaxResult.success(posts);
    }
}
