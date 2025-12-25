package com.center.medical.datamove.oracle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.oracle.bean.model.Comboandfzx;
import com.center.medical.datamove.oracle.service.ComboandfzxService;
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
 * 最小套餐与分中心关联表(Comboandfzx)接口
 *
 * @author ay
 * @since 2023-07-18 09:17:39
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "最小套餐与分中心关联表")
@RequestMapping("comboandfzx")
public class ComboandfzxController extends BaseController {
    /**
     * 服务对象
     */
    private final ComboandfzxService comboandfzxService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param comboandfzx     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询最小套餐与分中心关联表")
    public R<IPage<Comboandfzx>> getPage(PageParamSimple pageParamSimple, Comboandfzx comboandfzx) {
        PageParam<Comboandfzx> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.comboandfzxService.getPage(page, comboandfzx));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查最小套餐与分中心关联表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Comboandfzx> selectOne(@PathVariable String id) {
        return R.ok(this.comboandfzxService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param comboandfzx 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加最小套餐与分中心关联表")
    @Log(title = "最小套餐与分中心关联表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"comboandfzx.id"})
    public R insert(@RequestBody Comboandfzx comboandfzx) {
        return R.toResult(this.comboandfzxService.save(comboandfzx));
    }

    /**
     * 修改数据
     *
     * @param comboandfzx 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新最小套餐与分中心关联表")
    @Log(title = "最小套餐与分中心关联表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Comboandfzx comboandfzx) {
        return R.toResult(this.comboandfzxService.updateById(comboandfzx));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除最小套餐与分中心关联表")
    @Log(title = "最小套餐与分中心关联表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.comboandfzxService.removeByIds(ids));
    }
}

