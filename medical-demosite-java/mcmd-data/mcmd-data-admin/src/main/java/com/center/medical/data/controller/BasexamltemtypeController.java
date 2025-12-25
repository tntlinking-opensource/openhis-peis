package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Basexamltemtype;
import com.center.medical.data.bean.param.ExamItemTypePrama;
import com.center.medical.data.service.BasexamltemtypeService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 检查项目类型(Basexamltemtype)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-17 09:05:09
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "检查项目类型")
@RequestMapping("basexamltemtype")
public class BasexamltemtypeController extends BaseController {
    /**
     * 服务对象
     */
    private final BasexamltemtypeService basexamltemtypeService;
    private final MapperFacade mapperFacade;

    //TODO 权限设置

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
//    @PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询JC检查项目类型列表")
    public R<IPage<Basexamltemtype>> selectAll(PageParamSimple pageParamSimple, ExamItemTypePrama param) {
        PageParam<Basexamltemtype> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.basexamltemtypeService.getPage(page, param));
    }

    /**
     * 根据输入码查询
     *
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/list")
//    @PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据输入码查询", notes = "根据输入码查询JC检查项目类型列表")
    public R<List<Basexamltemtype>> list(ExamItemTypePrama param) {
        return R.ok(this.basexamltemtypeService.getList(param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查JC检查项目类型表详情")
    public R<Basexamltemtype> selectOne(@PathVariable String id) {
        return R.ok(this.basexamltemtypeService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param basexamltemtype 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加JC检查项目类型表")
    @Log(title = "JC检查项目类型表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"basexamltemtype.id"})
    public R insert(@RequestBody Basexamltemtype basexamltemtype) {
        return R.toResult(this.basexamltemtypeService.saOrUp(basexamltemtype));
    }

    /**
     * 修改数据
     *
     * @param basexamltemtype 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新JC检查项目类型表")
    @Log(title = "JC检查项目类型表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Basexamltemtype basexamltemtype) {
        return R.toResult(this.basexamltemtypeService.saOrUp(basexamltemtype));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除JC检查项目类型表")
    @Log(title = "JC检查项目类型表", businessType = BusinessType.DELETE)
    public R delete(@PathVariable List<String> ids) {

        return R.toResult(this.basexamltemtypeService.delete(ids));
    }
}

