package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.SysOperLog;
import com.center.medical.datamove.common.service.SysOperLogService;
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
 * 操作日志记录(SysOperLog)接口
 *
 * @author ay
 * @since 2023-07-17 20:54:36
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "操作日志记录")
@RequestMapping("sysOperLog")
public class SysOperLogController extends BaseController {
    /**
     * 服务对象
     */
    private final SysOperLogService sysOperLogService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param sysOperLog      查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询操作日志记录")
    public R<IPage<SysOperLog>> getPage(PageParamSimple pageParamSimple, SysOperLog sysOperLog) {
        PageParam<SysOperLog> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysOperLogService.getPage(page, sysOperLog));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param operId 主键
     * @return 单条数据
     */
    @GetMapping("{operId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据operId查操作日志记录详情")
    @ApiImplicitParam(name = "operId", value = "要查询的对象的主键{operId}")
    public R<SysOperLog> selectOne(@PathVariable Long operId) {
        return R.ok(this.sysOperLogService.getInfoById(operId));
    }

    /**
     * 新增数据
     *
     * @param sysOperLog 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加操作日志记录")
    @Log(title = "操作日志记录", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysOperLog.operId"})
    public R insert(@RequestBody SysOperLog sysOperLog) {
        return R.toResult(this.sysOperLogService.save(sysOperLog));
    }

    /**
     * 修改数据
     *
     * @param sysOperLog 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新操作日志记录")
    @Log(title = "操作日志记录", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysOperLog sysOperLog) {
        return R.toResult(this.sysOperLogService.updateById(sysOperLog));
    }

    /**
     * 删除数据
     *
     * @param operIds 删除的对象主键{operId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除操作日志记录")
    @Log(title = "操作日志记录", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{operId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> operIds) {
        return R.toResult(this.sysOperLogService.removeByIds(operIds));
    }
}

