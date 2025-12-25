package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.model.BusinessCat;
import com.center.medical.data.bean.param.BusinessCatParam;
import com.center.medical.data.service.BusinessCatService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 基础数据-业务类型：收费项目关联业务类型，主要用于业务业绩的统计
 *
 * @author 路飞船长
 * @since 2023-02-28 15:41:02
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "基础数据-业务类型")
@RequestMapping("data/businessCat")
public class BusinessCatController extends BaseController {
    /**
     * 服务对象
     */
    private final BusinessCatService businessCatService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询业务类型")
    public R<IPage<BusinessCat>> getPage(PageParamSimple pageParamSimple, BusinessCatParam param) {
        PageParam<BusinessCat> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.businessCatService.getPage(page, param));
    }

    /**
     * 获取列表
     *
     * @param businessCat 查询条件
     * @return 所有数据
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取列表", notes = "获取列表")
    public R<List<BusinessCat>> getList(BusinessCat businessCat) {
        return R.ok(this.businessCatService.getList(businessCat));
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
    public R<BusinessCat> selectOne(@PathVariable Long typeId) {
        return R.ok(this.businessCatService.getInfoById(typeId));
    }

    /**
     * 新增数据
     *
     * @param businessCat 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加业务类型")
    @Log(title = "业务类型", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"businessCat.typeId"})
    public R insert(@RequestBody BusinessCat businessCat) {
        return R.toResult(this.businessCatService.save(businessCat));
    }

    /**
     * 修改数据
     *
     * @param businessCat 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新业务类型")
    @Log(title = "业务类型", businessType = BusinessType.UPDATE)
    public R update(@RequestBody BusinessCat businessCat) {
        return R.toResult(this.businessCatService.updateById(businessCat));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{typeId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除业务类型")
    @Log(title = "业务类型", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{typeId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> ids) {
        // TODO wait 判断该类型下是否存在与之管理的收费项目
        return R.toResult(this.businessCatService.removeByIds(ids));
    }


    /**
     * 导出业务类型
     * @param response
     * @param param
     */
    @Log(title = "业务类型", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "导出", notes = "导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusinessCatParam param) {
        List<BusinessCat> list = businessCatService.getExportData(param);
        ExcelUtil<BusinessCat> util = new ExcelUtil<BusinessCat>(BusinessCat.class);
        util.exportExcel(response, list, "业务类型");
    }


}

