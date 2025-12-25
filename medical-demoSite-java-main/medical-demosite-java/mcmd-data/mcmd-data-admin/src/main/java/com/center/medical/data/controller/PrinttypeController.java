package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Printtype;
import com.center.medical.data.bean.param.PrinttypePrama;
import com.center.medical.data.service.PrinttypeService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收费项目分类设置(Printtype)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-18 09:07:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "收费项目分类设置")
@RequestMapping("printtype")
public class PrinttypeController extends BaseController {
    /**
     * 服务对象
     */
    private final PrinttypeService printtypeService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询销售打印分类设置")
    public R<IPage<Printtype>> getPage(PageParamSimple pageParamSimple, PrinttypePrama param) {
        PageParam<Printtype> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.printtypeService.getPage(page, param));
    }

    /**
     * 根据输入码销售打印分类列表
     *
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/list")
//    @PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据输入码查询", notes = "根据输入码销售打印分类列表")
    public R<List<Printtype>> list(PrinttypePrama param) {
        return R.ok(this.printtypeService.getList(param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查销售打印分类设置详情")
    public R<Printtype> selectOne(@PathVariable String id) {
        return R.ok(this.printtypeService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param printtype 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加销售打印分类设置")
    @Log(title = "销售打印分类设置", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"printtype.id"})
    public R insert(@RequestBody Printtype printtype) {
        return R.toResult(this.printtypeService.saOrUp(printtype));
    }

    /**
     * 修改数据
     *
     * @param printtype 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新销售打印分类设置")
    @Log(title = "销售打印分类设置", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Printtype printtype) {
        return R.toResult(this.printtypeService.saOrUp(printtype));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除销售打印分类设置")
    @Log(title = "销售打印分类设置", businessType = BusinessType.DELETE)
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.printtypeService.removeByIds(ids));
    }
}

