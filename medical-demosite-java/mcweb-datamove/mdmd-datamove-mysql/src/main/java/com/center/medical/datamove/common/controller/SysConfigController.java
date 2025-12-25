package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.SysConfig;
import com.center.medical.datamove.common.service.SysConfigService;
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
 * 参数配置表(SysConfig)接口
 *
 * @author ay
 * @since 2023-07-17 20:54:33
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "参数配置表")
@RequestMapping("sysConfig")
public class SysConfigController extends BaseController {
    /**
     * 服务对象
     */
    private final SysConfigService sysConfigService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param sysConfig       查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询参数配置表")
    public R<IPage<SysConfig>> getPage(PageParamSimple pageParamSimple, SysConfig sysConfig) {
        PageParam<SysConfig> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysConfigService.getPage(page, sysConfig));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param configId 主键
     * @return 单条数据
     */
    @GetMapping("{configId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据configId查参数配置表详情")
    @ApiImplicitParam(name = "configId", value = "要查询的对象的主键{configId}")
    public R<SysConfig> selectOne(@PathVariable Integer configId) {
        return R.ok(this.sysConfigService.getInfoById(configId));
    }

    /**
     * 新增数据
     *
     * @param sysConfig 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加参数配置表")
    @Log(title = "参数配置表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysConfig.configId"})
    public R insert(@RequestBody SysConfig sysConfig) {
        return R.toResult(this.sysConfigService.save(sysConfig));
    }

    /**
     * 修改数据
     *
     * @param sysConfig 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新参数配置表")
    @Log(title = "参数配置表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysConfig sysConfig) {
        return R.toResult(this.sysConfigService.updateById(sysConfig));
    }

    /**
     * 删除数据
     *
     * @param configIds 删除的对象主键{configId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除参数配置表")
    @Log(title = "参数配置表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{configId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Integer> configIds) {
        return R.toResult(this.sysConfigService.removeByIds(configIds));
    }
}

