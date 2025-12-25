package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.GenTableColumn;
import com.center.medical.datamove.common.service.GenTableColumnService;
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
 * 代码生成业务表字段(GenTableColumn)接口
 *
 * @author ay
 * @since 2023-07-17 20:45:03
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "代码生成业务表字段")
@RequestMapping("genTableColumn")
public class GenTableColumnController extends BaseController {
    /**
     * 服务对象
     */
    private final GenTableColumnService genTableColumnService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param genTableColumn  查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询代码生成业务表字段")
    public R<IPage<GenTableColumn>> getPage(PageParamSimple pageParamSimple, GenTableColumn genTableColumn) {
        PageParam<GenTableColumn> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.genTableColumnService.getPage(page, genTableColumn));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param columnId 主键
     * @return 单条数据
     */
    @GetMapping("{columnId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据columnId查代码生成业务表字段详情")
    @ApiImplicitParam(name = "columnId", value = "要查询的对象的主键{columnId}")
    public R<GenTableColumn> selectOne(@PathVariable Long columnId) {
        return R.ok(this.genTableColumnService.getInfoById(columnId));
    }

    /**
     * 新增数据
     *
     * @param genTableColumn 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加代码生成业务表字段")
    @Log(title = "代码生成业务表字段", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"genTableColumn.columnId"})
    public R insert(@RequestBody GenTableColumn genTableColumn) {
        return R.toResult(this.genTableColumnService.save(genTableColumn));
    }

    /**
     * 修改数据
     *
     * @param genTableColumn 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新代码生成业务表字段")
    @Log(title = "代码生成业务表字段", businessType = BusinessType.UPDATE)
    public R update(@RequestBody GenTableColumn genTableColumn) {
        return R.toResult(this.genTableColumnService.updateById(genTableColumn));
    }

    /**
     * 删除数据
     *
     * @param columnIds 删除的对象主键{columnId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除代码生成业务表字段")
    @Log(title = "代码生成业务表字段", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{columnId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> columnIds) {
        return R.toResult(this.genTableColumnService.removeByIds(columnIds));
    }
}

