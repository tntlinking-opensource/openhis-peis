package com.center.medical.system.controller.system;

import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.AjaxResult;
import com.center.medical.common.core.domain.entity.SysNotice;
import com.center.medical.common.core.page.TableDataInfo;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.system.service.ISysNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公告 信息操作处理
 *
 * @author 路飞
 */
@Api(tags = "公告信息")
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
    @Resource
    private ISysNoticeService noticeService;

    /**
     * 获取通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("/list")
    @ApiOperation(value = "获取通知公告列表", notes = "获取通知公告列表")
    public TableDataInfo list(SysNotice notice) {
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/{noticeId}")
    @ApiOperation(value = "根据通知公告编号获取详细信息", notes = "根据通知公告编号获取详细信息")
    public AjaxResult getInfo(@PathVariable Long noticeId) {
        return AjaxResult.success(noticeService.selectNoticeById(noticeId));
    }

    /**
     * 新增通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "通知公告-新增通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增通知公告", notes = "新增通知公告")
    public AjaxResult add(@Validated @RequestBody SysNotice notice) {
        notice.setCreateBy(getUsername());
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "通知公告-修改通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改通知公告", notes = "修改通知公告")
    public AjaxResult edit(@Validated @RequestBody SysNotice notice) {
        notice.setUpdateBy(getUsername());
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除通知公告
     */
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "通知公告-删除通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    @ApiOperation(value = "删除通知公告", notes = "删除通知公告")
    public AjaxResult remove(@PathVariable Long[] noticeIds) {
        return toAjax(noticeService.deleteNoticeByIds(noticeIds));
    }
}
