package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.MdFxNegative;
import com.center.medical.datamove.common.service.MdFxNegativeService;
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
 * 综合分析-阴性小结(MdFxNegative)接口
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "综合分析-阴性小结")
@RequestMapping("mdFxNegative")
public class MdFxNegativeController extends BaseController {
    /**
     * 服务对象
     */
    private final MdFxNegativeService mdFxNegativeService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param mdFxNegative    查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询综合分析-阴性小结")
    public R<IPage<MdFxNegative>> getPage(PageParamSimple pageParamSimple, MdFxNegative mdFxNegative) {
        PageParam<MdFxNegative> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.mdFxNegativeService.getPage(page, mdFxNegative));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查综合分析-阴性小结详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<MdFxNegative> selectOne(@PathVariable String id) {
        return R.ok(this.mdFxNegativeService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param mdFxNegative 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加综合分析-阴性小结")
    @Log(title = "综合分析-阴性小结", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"mdFxNegative.id"})
    public R insert(@RequestBody MdFxNegative mdFxNegative) {
        return R.toResult(this.mdFxNegativeService.save(mdFxNegative));
    }

    /**
     * 修改数据
     *
     * @param mdFxNegative 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新综合分析-阴性小结")
    @Log(title = "综合分析-阴性小结", businessType = BusinessType.UPDATE)
    public R update(@RequestBody MdFxNegative mdFxNegative) {
        return R.toResult(this.mdFxNegativeService.updateById(mdFxNegative));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除综合分析-阴性小结")
    @Log(title = "综合分析-阴性小结", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.mdFxNegativeService.removeByIds(ids));
    }
}

