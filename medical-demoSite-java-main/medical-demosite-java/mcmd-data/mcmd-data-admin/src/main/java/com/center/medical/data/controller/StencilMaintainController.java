package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.StencilMaintain;
import com.center.medical.data.service.StencilMaintainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * WORD模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)(StencilMaintain)表控制层
 *
 * @author ay
 * @since 2022-11-14 17:10:09
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "WORD模板维护表")
@RequestMapping("stencilMaintain")
public class StencilMaintainController extends BaseController {
    /**
     * 服务对象
     */
    private final StencilMaintainService stencilMaintainService;


    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param stencilMaintain 查询实体
     * @return 所有数据
     */
    @GetMapping("/getListData")
	//@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)")
    public R<IPage<StencilMaintain>> getListData(PageParam<StencilMaintain> page, StencilMaintain stencilMaintain) {
        return R.ok(this.stencilMaintainService.getList(page, stencilMaintain));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
	//@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)详情")
    public R<StencilMaintain> selectOne(@PathVariable String id) {
        return R.ok(this.stencilMaintainService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param stencilMaintain 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
	//@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "保存或编辑", notes = "保存或编辑)")
    @Log(title = "保存或编辑)", businessType = BusinessType.INSERT)
    public R saveOrUpdate(@RequestBody StencilMaintain stencilMaintain) {
        stencilMaintain.setIsDelete(1);
        String status = stencilMaintainService.saveOrUpdateStencilMaintain(stencilMaintain);
        return R.ok(status);
    }


    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/remove/{ids}")
	//@PreAuthorize("@ss.hasPermi('::del')")
    @ApiOperation(value = "删除", notes = "删除模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)")
    @Log(title = "模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)", businessType = BusinessType.DELETE)
    public R delete(@PathVariable List<String> ids) {
        //逻辑删除，0.已删除 1.未删除
        StencilMaintain stencilMaintain = new StencilMaintain();
        stencilMaintain.setIsDelete(0);
        boolean b = stencilMaintainService.update(stencilMaintain, new QueryWrapper<StencilMaintain>().in("id", ids));
        return R.toResult(b);
    }
}

