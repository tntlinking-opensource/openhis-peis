package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.MdOrderandcombo;
import com.center.medical.datamove.common.service.MdOrderandcomboService;
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
 * 订单与套餐关联表(MdOrderandcombo)接口
 *
 * @author ay
 * @since 2023-07-17 20:46:29
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "订单与套餐关联表")
@RequestMapping("mdOrderandcombo")
public class MdOrderandcomboController extends BaseController {
    /**
     * 服务对象
     */
    private final MdOrderandcomboService mdOrderandcomboService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param mdOrderandcombo 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询订单与套餐关联表")
    public R<IPage<MdOrderandcombo>> getPage(PageParamSimple pageParamSimple, MdOrderandcombo mdOrderandcombo) {
        PageParam<MdOrderandcombo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.mdOrderandcomboService.getPage(page, mdOrderandcombo));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查订单与套餐关联表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<MdOrderandcombo> selectOne(@PathVariable String id) {
        return R.ok(this.mdOrderandcomboService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param mdOrderandcombo 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加订单与套餐关联表")
    @Log(title = "订单与套餐关联表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"mdOrderandcombo.id"})
    public R insert(@RequestBody MdOrderandcombo mdOrderandcombo) {
        return R.toResult(this.mdOrderandcomboService.save(mdOrderandcombo));
    }

    /**
     * 修改数据
     *
     * @param mdOrderandcombo 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新订单与套餐关联表")
    @Log(title = "订单与套餐关联表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody MdOrderandcombo mdOrderandcombo) {
        return R.toResult(this.mdOrderandcomboService.updateById(mdOrderandcombo));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除订单与套餐关联表")
    @Log(title = "订单与套餐关联表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.mdOrderandcomboService.removeByIds(ids));
    }
}

