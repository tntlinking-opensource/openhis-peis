package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.MdUserMappingSys;
import com.center.medical.datamove.common.service.MdUserMappingSysService;
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
 * 用户映射系统(MdUserMappingSys)接口
 *
 * @author ay
 * @since 2023-07-17 20:49:22
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "用户映射系统")
@RequestMapping("mdUserMappingSys")
public class MdUserMappingSysController extends BaseController {
    /**
     * 服务对象
     */
    private final MdUserMappingSysService mdUserMappingSysService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple  分页参数
     * @param mdUserMappingSys 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询用户映射系统")
    public R<IPage<MdUserMappingSys>> getPage(PageParamSimple pageParamSimple, MdUserMappingSys mdUserMappingSys) {
        PageParam<MdUserMappingSys> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.mdUserMappingSysService.getPage(page, mdUserMappingSys));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param systemId 主键
     * @return 单条数据
     */
    @GetMapping("{systemId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据systemId查用户映射系统详情")
    @ApiImplicitParam(name = "systemId", value = "要查询的对象的主键{systemId}")
    public R<MdUserMappingSys> selectOne(@PathVariable String systemId) {
        return R.ok(this.mdUserMappingSysService.getInfoById(systemId));
    }

    /**
     * 新增数据
     *
     * @param mdUserMappingSys 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加用户映射系统")
    @Log(title = "用户映射系统", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"mdUserMappingSys.systemId"})
    public R insert(@RequestBody MdUserMappingSys mdUserMappingSys) {
        return R.toResult(this.mdUserMappingSysService.save(mdUserMappingSys));
    }

    /**
     * 修改数据
     *
     * @param mdUserMappingSys 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新用户映射系统")
    @Log(title = "用户映射系统", businessType = BusinessType.UPDATE)
    public R update(@RequestBody MdUserMappingSys mdUserMappingSys) {
        return R.toResult(this.mdUserMappingSysService.updateById(mdUserMappingSys));
    }

    /**
     * 删除数据
     *
     * @param systemIds 删除的对象主键{systemId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除用户映射系统")
    @Log(title = "用户映射系统", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{systemId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> systemIds) {
        return R.toResult(this.mdUserMappingSysService.removeByIds(systemIds));
    }
}

