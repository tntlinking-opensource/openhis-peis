package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.SysLogininfor;
import com.center.medical.datamove.common.service.SysLogininforService;
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
 * 系统访问记录(SysLogininfor)接口
 *
 * @author ay
 * @since 2023-07-17 20:54:35
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "系统访问记录")
@RequestMapping("sysLogininfor")
public class SysLogininforController extends BaseController {
    /**
     * 服务对象
     */
    private final SysLogininforService sysLogininforService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param sysLogininfor   查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询系统访问记录")
    public R<IPage<SysLogininfor>> getPage(PageParamSimple pageParamSimple, SysLogininfor sysLogininfor) {
        PageParam<SysLogininfor> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysLogininforService.getPage(page, sysLogininfor));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param infoId 主键
     * @return 单条数据
     */
    @GetMapping("{infoId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据infoId查系统访问记录详情")
    @ApiImplicitParam(name = "infoId", value = "要查询的对象的主键{infoId}")
    public R<SysLogininfor> selectOne(@PathVariable Long infoId) {
        return R.ok(this.sysLogininforService.getInfoById(infoId));
    }

    /**
     * 新增数据
     *
     * @param sysLogininfor 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加系统访问记录")
    @Log(title = "系统访问记录", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysLogininfor.infoId"})
    public R insert(@RequestBody SysLogininfor sysLogininfor) {
        return R.toResult(this.sysLogininforService.save(sysLogininfor));
    }

    /**
     * 修改数据
     *
     * @param sysLogininfor 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新系统访问记录")
    @Log(title = "系统访问记录", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysLogininfor sysLogininfor) {
        return R.toResult(this.sysLogininforService.updateById(sysLogininfor));
    }

    /**
     * 删除数据
     *
     * @param infoIds 删除的对象主键{infoId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除系统访问记录")
    @Log(title = "系统访问记录", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{infoId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> infoIds) {
        return R.toResult(this.sysLogininforService.removeByIds(infoIds));
    }
}

