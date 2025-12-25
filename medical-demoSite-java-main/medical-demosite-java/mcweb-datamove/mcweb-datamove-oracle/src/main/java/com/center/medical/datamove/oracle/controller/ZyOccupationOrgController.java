package com.center.medical.datamove.oracle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.oracle.bean.model.ZyOccupationOrg;
import com.center.medical.datamove.oracle.service.ZyOccupationOrgService;
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
 * JC职业病体检机构(ZyOccupationOrg)接口
 *
 * @author ay
 * @since 2023-07-18 09:30:59
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "JC职业病体检机构")
@RequestMapping("zyOccupationOrg")
public class ZyOccupationOrgController extends BaseController {
    /**
     * 服务对象
     */
    private final ZyOccupationOrgService zyOccupationOrgService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param zyOccupationOrg 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询JC职业病体检机构")
    public R<IPage<ZyOccupationOrg>> getPage(PageParamSimple pageParamSimple, ZyOccupationOrg zyOccupationOrg) {
        PageParam<ZyOccupationOrg> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.zyOccupationOrgService.getPage(page, zyOccupationOrg));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param orgCode 主键
     * @return 单条数据
     */
    @GetMapping("{orgCode}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据orgCode查JC职业病体检机构详情")
    @ApiImplicitParam(name = "orgCode", value = "要查询的对象的主键{orgCode}")
    public R<ZyOccupationOrg> selectOne(@PathVariable Object orgCode) {
        return R.ok(this.zyOccupationOrgService.getInfoById(orgCode));
    }

    /**
     * 新增数据
     *
     * @param zyOccupationOrg 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加JC职业病体检机构")
    @Log(title = "JC职业病体检机构", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"zyOccupationOrg.orgCode"})
    public R insert(@RequestBody ZyOccupationOrg zyOccupationOrg) {
        return R.toResult(this.zyOccupationOrgService.save(zyOccupationOrg));
    }

    /**
     * 修改数据
     *
     * @param zyOccupationOrg 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新JC职业病体检机构")
    @Log(title = "JC职业病体检机构", businessType = BusinessType.UPDATE)
    public R update(@RequestBody ZyOccupationOrg zyOccupationOrg) {
        return R.toResult(this.zyOccupationOrgService.updateById(zyOccupationOrg));
    }

    /**
     * 删除数据
     *
     * @param orgCodes 删除的对象主键{orgCode}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除JC职业病体检机构")
    @Log(title = "JC职业病体检机构", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{orgCode}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> orgCodes) {
        return R.toResult(this.zyOccupationOrgService.removeByIds(orgCodes));
    }
}

