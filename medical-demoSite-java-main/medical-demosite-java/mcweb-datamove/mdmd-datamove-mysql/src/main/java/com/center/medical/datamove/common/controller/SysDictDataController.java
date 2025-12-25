package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.SysDictData;
import com.center.medical.datamove.common.service.SysDictDataService;
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
 * 字典数据表(SysDictData)接口
 *
 * @author ay
 * @since 2023-07-17 20:54:34
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "字典数据表")
@RequestMapping("sysDictData")
public class SysDictDataController extends BaseController {
    /**
     * 服务对象
     */
    private final SysDictDataService sysDictDataService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param sysDictData     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询字典数据表")
    public R<IPage<SysDictData>> getPage(PageParamSimple pageParamSimple, SysDictData sysDictData) {
        PageParam<SysDictData> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysDictDataService.getPage(page, sysDictData));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param dictCode 主键
     * @return 单条数据
     */
    @GetMapping("{dictCode}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据dictCode查字典数据表详情")
    @ApiImplicitParam(name = "dictCode", value = "要查询的对象的主键{dictCode}")
    public R<SysDictData> selectOne(@PathVariable Long dictCode) {
        return R.ok(this.sysDictDataService.getInfoById(dictCode));
    }

    /**
     * 新增数据
     *
     * @param sysDictData 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加字典数据表")
    @Log(title = "字典数据表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysDictData.dictCode"})
    public R insert(@RequestBody SysDictData sysDictData) {
        return R.toResult(this.sysDictDataService.save(sysDictData));
    }

    /**
     * 修改数据
     *
     * @param sysDictData 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新字典数据表")
    @Log(title = "字典数据表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysDictData sysDictData) {
        return R.toResult(this.sysDictDataService.updateById(sysDictData));
    }

    /**
     * 删除数据
     *
     * @param dictCodes 删除的对象主键{dictCode}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除字典数据表")
    @Log(title = "字典数据表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{dictCode}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> dictCodes) {
        return R.toResult(this.sysDictDataService.removeByIds(dictCodes));
    }
}

