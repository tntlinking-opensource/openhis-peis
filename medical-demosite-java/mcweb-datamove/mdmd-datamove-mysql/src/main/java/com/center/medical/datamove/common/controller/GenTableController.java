package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.GenTable;
import com.center.medical.datamove.common.service.GenTableService;
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
 * 代码生成业务表(GenTable)接口
 *
 * @author ay
 * @since 2023-07-17 20:45:02
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "代码生成业务表")
@RequestMapping("genTable")
public class GenTableController extends BaseController {
    /**
     * 服务对象
     */
    private final GenTableService genTableService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param genTable        查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询代码生成业务表")
    public R<IPage<GenTable>> getPage(PageParamSimple pageParamSimple, GenTable genTable) {
        PageParam<GenTable> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.genTableService.getPage(page, genTable));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param tableId 主键
     * @return 单条数据
     */
    @GetMapping("{tableId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据tableId查代码生成业务表详情")
    @ApiImplicitParam(name = "tableId", value = "要查询的对象的主键{tableId}")
    public R<GenTable> selectOne(@PathVariable Long tableId) {
        return R.ok(this.genTableService.getInfoById(tableId));
    }

    /**
     * 新增数据
     *
     * @param genTable 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加代码生成业务表")
    @Log(title = "代码生成业务表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"genTable.tableId"})
    public R insert(@RequestBody GenTable genTable) {
        return R.toResult(this.genTableService.save(genTable));
    }

    /**
     * 修改数据
     *
     * @param genTable 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新代码生成业务表")
    @Log(title = "代码生成业务表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody GenTable genTable) {
        return R.toResult(this.genTableService.updateById(genTable));
    }

    /**
     * 删除数据
     *
     * @param tableIds 删除的对象主键{tableId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除代码生成业务表")
    @Log(title = "代码生成业务表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{tableId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> tableIds) {
        return R.toResult(this.genTableService.removeByIds(tableIds));
    }
}

