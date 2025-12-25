package com.center.medical.system.controller.version;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.system.bean.model.SysVersionDeploy;
import com.center.medical.system.service.SysVersionDeployService;
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
 * 版本控制-分中心版本更新记录(SysVersionDeploy)接口
 *
 * @author makejava
 * @since 2024-03-01 18:03:42
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "版本控制-分中心版本更新记录")
@RequestMapping("sysVersionDeploy")
public class SysVersionDeployController extends BaseController {
    /**
     * 服务对象
     */
    private final SysVersionDeployService sysVersionDeployService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple  分页参数
     * @param sysVersionDeploy 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询版本控制-分中心版本更新记录")
    public R<IPage<SysVersionDeploy>> getPage(PageParamSimple pageParamSimple, SysVersionDeploy sysVersionDeploy) {
        PageParam<SysVersionDeploy> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysVersionDeployService.getPage(page, sysVersionDeploy));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查版本控制-分中心版本更新记录详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<SysVersionDeploy> selectOne(@PathVariable String id) {
        return R.ok(this.sysVersionDeployService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param sysVersionDeploy 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加版本控制-分中心版本更新记录")
    @Log(title = "版本控制-分中心版本更新记录", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysVersionDeploy.id"})
    public R insert(@RequestBody SysVersionDeploy sysVersionDeploy) {
        return R.toResult(this.sysVersionDeployService.save(sysVersionDeploy));
    }

    /**
     * 修改数据
     *
     * @param sysVersionDeploy 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新版本控制-分中心版本更新记录")
    @Log(title = "版本控制-分中心版本更新记录", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysVersionDeploy sysVersionDeploy) {
        return R.toResult(this.sysVersionDeployService.updateById(sysVersionDeploy));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除版本控制-分中心版本更新记录")
    @Log(title = "版本控制-分中心版本更新记录", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.sysVersionDeployService.removeByIds(ids));
    }
}


