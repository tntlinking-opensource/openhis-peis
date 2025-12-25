package com.center.medical.appadmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.MdSysBranch;
import com.center.medical.appadmin.bean.param.MdSysBranchParam;
import com.center.medical.appadmin.bean.param.SysBranchSaOrUpParam;
import com.center.medical.appadmin.service.MdSysBranchService;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分中心维护表(MdSysBranch)接口
 *
 * @author ay
 * @since 2024-06-11 16:03:16
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "分中心维护表")
@RequestMapping("app/mdSysBranch")
public class MdSysBranchController extends BaseController {
    /**
     * 服务对象
     */
    private final MdSysBranchService mdSysBranchService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询分中心维护表")
    public R<IPage<MdSysBranch>> getPage(PageParamSimple pageParamSimple, MdSysBranchParam param) {
        PageParam<MdSysBranch> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.mdSysBranchService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查分中心维护表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<MdSysBranch> selectOne(@PathVariable Integer id) {
        return R.ok(this.mdSysBranchService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saOrUp")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加或修改", notes = "添加或修改分中心维护表")
    @Log(title = "分中心维护表", businessType = BusinessType.INSERT)
    public R insert(@RequestBody SysBranchSaOrUpParam param) {
        return R.toResult(this.mdSysBranchService.saOrUp(param));
    }



    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除分中心维护表")
    @Log(title = "分中心维护表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Integer> ids) {
        return R.toResult(this.mdSysBranchService.deleteByIds(ids));
    }

}
