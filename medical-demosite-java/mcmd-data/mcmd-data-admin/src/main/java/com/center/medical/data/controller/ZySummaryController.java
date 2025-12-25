package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.ZySummary;
import com.center.medical.data.service.ZySummaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 职业检查设置-职业健康检查结论(ZySummary)表控制层
 *
 * @author ay
 * @since 2022-11-16 18:44:54
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业检查设置-职业健康检查结论")
@RequestMapping("zySummary")
public class ZySummaryController extends BaseController {
    /**
     * 服务对象
     */
    private final ZySummaryService zySummaryService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param zySummary       查询实体
     * @return 所有数据
     */
    @GetMapping("/getListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询JC职业病检查结论")
    public R<IPage<ZySummary>> getListData(PageParamSimple pageParamSimple, ZySummary zySummary) {
        PageParam<ZySummary> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.zySummaryService.getList(page, zySummary));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查JC职业病检查结论详情")
    public R<ZySummary> selectOne(@PathVariable String id) {
        return R.ok(this.zySummaryService.getInfoById(id));
    }

    /**
     * 新增或修改数据
     *
     * @param zySummary 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "新增或修改数据", notes = "新增或修改JC职业病检查结论")
    @Log(title = "JC职业病检查结论", businessType = BusinessType.INSERT)
    public R saveOrUpdate(@RequestBody ZySummary zySummary) {
        String s = zySummaryService.saveOrUpdateZySummary(zySummary);
        return R.ok(s);
    }

    /**
     * 编辑返回数据
     *
     * @param id 实体对象
     * @return
     */
    @GetMapping("/edit/{id}")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "编辑返回数据", notes = "编辑返回数据")
    @Log(title = "JC职业病检查结论", businessType = BusinessType.UPDATE)
    public R edit(@PathVariable String id) {
        return R.ok(zySummaryService.getInfoById(id));
    }

    /**
     * 逻辑删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/remove/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "逻辑删除JC职业病检查结论")
    @Log(title = "JC职业病检查结论", businessType = BusinessType.DELETE)
    public R delete(@PathVariable String ids) {
        String s = zySummaryService.removeZySummary(ids);
        return R.ok(s);
    }
}

