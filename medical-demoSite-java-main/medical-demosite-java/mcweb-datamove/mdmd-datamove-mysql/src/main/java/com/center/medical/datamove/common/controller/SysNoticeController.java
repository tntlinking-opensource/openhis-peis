package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.SysNotice;
import com.center.medical.datamove.common.service.SysNoticeService;
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
 * 通知公告表(SysNotice)接口
 *
 * @author ay
 * @since 2023-07-17 20:54:36
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "通知公告表")
@RequestMapping("sysNotice")
public class SysNoticeController extends BaseController {
    /**
     * 服务对象
     */
    private final SysNoticeService sysNoticeService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param sysNotice       查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询通知公告表")
    public R<IPage<SysNotice>> getPage(PageParamSimple pageParamSimple, SysNotice sysNotice) {
        PageParam<SysNotice> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysNoticeService.getPage(page, sysNotice));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param noticeId 主键
     * @return 单条数据
     */
    @GetMapping("{noticeId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据noticeId查通知公告表详情")
    @ApiImplicitParam(name = "noticeId", value = "要查询的对象的主键{noticeId}")
    public R<SysNotice> selectOne(@PathVariable Integer noticeId) {
        return R.ok(this.sysNoticeService.getInfoById(noticeId));
    }

    /**
     * 新增数据
     *
     * @param sysNotice 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加通知公告表")
    @Log(title = "通知公告表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysNotice.noticeId"})
    public R insert(@RequestBody SysNotice sysNotice) {
        return R.toResult(this.sysNoticeService.save(sysNotice));
    }

    /**
     * 修改数据
     *
     * @param sysNotice 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新通知公告表")
    @Log(title = "通知公告表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysNotice sysNotice) {
        return R.toResult(this.sysNoticeService.updateById(sysNotice));
    }

    /**
     * 删除数据
     *
     * @param noticeIds 删除的对象主键{noticeId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除通知公告表")
    @Log(title = "通知公告表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{noticeId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Integer> noticeIds) {
        return R.toResult(this.sysNoticeService.removeByIds(noticeIds));
    }
}

