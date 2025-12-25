package com.center.medical.syncpf.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sync.bean.model.SyncDataLog;
import com.center.medical.sync.service.SyncDataLogService;
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
 * 同步数据操作记录(SyncDataLog)表控制层
 *
 * @author 路飞船长
 * @since 2023-02-02 15:14:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "同步数据操作记录")
@RequestMapping("syncDataLog")
public class SyncDataLogController extends BaseController {
    /**
     * 服务对象
     */
    private final SyncDataLogService syncDataLogService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param syncDataLog     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询同步数据操作记录")
    public R<IPage<SyncDataLog>> getPage(PageParamSimple pageParamSimple, SyncDataLog syncDataLog) {
        PageParam<SyncDataLog> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.syncDataLogService.getPage(page, syncDataLog));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param logId 主键
     * @return 单条数据
     */
    @GetMapping("{logId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据logId查同步数据操作记录详情")
    @ApiImplicitParam(name = "logId", value = "要查询的对象的主键{logId}")
    public R<SyncDataLog> selectOne(@PathVariable Long logId) {
        return R.ok(this.syncDataLogService.getInfoById(logId));
    }

    /**
     * 新增数据
     *
     * @param syncDataLog 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加同步数据操作记录")
    @Log(title = "同步数据操作记录", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"syncDataLog.logId"})
    public R insert(@RequestBody SyncDataLog syncDataLog) {
        return R.toResult(this.syncDataLogService.save(syncDataLog));
    }

    /**
     * 修改数据
     *
     * @param syncDataLog 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新同步数据操作记录")
    @Log(title = "同步数据操作记录", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SyncDataLog syncDataLog) {
        return R.toResult(this.syncDataLogService.updateById(syncDataLog));
    }

    /**
     * 删除数据
     *
     * @param logIds 删除的对象主键{logId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除同步数据操作记录")
    @Log(title = "同步数据操作记录", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{logId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> logIds) {
        return R.toResult(this.syncDataLogService.removeByIds(logIds));
    }
}

