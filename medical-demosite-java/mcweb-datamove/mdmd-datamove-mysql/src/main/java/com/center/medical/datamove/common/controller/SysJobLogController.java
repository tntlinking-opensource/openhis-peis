package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.SysJobLog;
import com.center.medical.datamove.common.service.SysJobLogService;
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
 * 定时任务调度日志表(SysJobLog)接口
 *
 * @author ay
 * @since 2023-07-17 20:54:35
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "定时任务调度日志表")
@RequestMapping("sysJobLog")
public class SysJobLogController extends BaseController {
    /**
     * 服务对象
     */
    private final SysJobLogService sysJobLogService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param sysJobLog       查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询定时任务调度日志表")
    public R<IPage<SysJobLog>> getPage(PageParamSimple pageParamSimple, SysJobLog sysJobLog) {
        PageParam<SysJobLog> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysJobLogService.getPage(page, sysJobLog));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param jobLogId 主键
     * @return 单条数据
     */
    @GetMapping("{jobLogId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据jobLogId查定时任务调度日志表详情")
    @ApiImplicitParam(name = "jobLogId", value = "要查询的对象的主键{jobLogId}")
    public R<SysJobLog> selectOne(@PathVariable Long jobLogId) {
        return R.ok(this.sysJobLogService.getInfoById(jobLogId));
    }

    /**
     * 新增数据
     *
     * @param sysJobLog 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加定时任务调度日志表")
    @Log(title = "定时任务调度日志表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysJobLog.jobLogId"})
    public R insert(@RequestBody SysJobLog sysJobLog) {
        return R.toResult(this.sysJobLogService.save(sysJobLog));
    }

    /**
     * 修改数据
     *
     * @param sysJobLog 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新定时任务调度日志表")
    @Log(title = "定时任务调度日志表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysJobLog sysJobLog) {
        return R.toResult(this.sysJobLogService.updateById(sysJobLog));
    }

    /**
     * 删除数据
     *
     * @param jobLogIds 删除的对象主键{jobLogId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除定时任务调度日志表")
    @Log(title = "定时任务调度日志表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{jobLogId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> jobLogIds) {
        return R.toResult(this.sysJobLogService.removeByIds(jobLogIds));
    }
}

