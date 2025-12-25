package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.MdBusinessCat;
import com.center.medical.datamove.common.service.MdBusinessCatService;
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
 * 业务类型(MdBusinessCat)接口
 *
 * @author ay
 * @since 2023-07-17 20:45:13
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "业务类型")
@RequestMapping("mdBusinessCat")
public class MdBusinessCatController extends BaseController {
    /**
     * 服务对象
     */
    private final MdBusinessCatService mdBusinessCatService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param mdBusinessCat   查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询业务类型")
    public R<IPage<MdBusinessCat>> getPage(PageParamSimple pageParamSimple, MdBusinessCat mdBusinessCat) {
        PageParam<MdBusinessCat> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.mdBusinessCatService.getPage(page, mdBusinessCat));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param typeId 主键
     * @return 单条数据
     */
    @GetMapping("{typeId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据typeId查业务类型详情")
    @ApiImplicitParam(name = "typeId", value = "要查询的对象的主键{typeId}")
    public R<MdBusinessCat> selectOne(@PathVariable Object typeId) {
        return R.ok(this.mdBusinessCatService.getInfoById(typeId));
    }

    /**
     * 新增数据
     *
     * @param mdBusinessCat 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加业务类型")
    @Log(title = "业务类型", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"mdBusinessCat.typeId"})
    public R insert(@RequestBody MdBusinessCat mdBusinessCat) {
        return R.toResult(this.mdBusinessCatService.save(mdBusinessCat));
    }

    /**
     * 修改数据
     *
     * @param mdBusinessCat 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新业务类型")
    @Log(title = "业务类型", businessType = BusinessType.UPDATE)
    public R update(@RequestBody MdBusinessCat mdBusinessCat) {
        return R.toResult(this.mdBusinessCatService.updateById(mdBusinessCat));
    }

    /**
     * 删除数据
     *
     * @param typeIds 删除的对象主键{typeId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除业务类型")
    @Log(title = "业务类型", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{typeId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> typeIds) {
        return R.toResult(this.mdBusinessCatService.removeByIds(typeIds));
    }
}

