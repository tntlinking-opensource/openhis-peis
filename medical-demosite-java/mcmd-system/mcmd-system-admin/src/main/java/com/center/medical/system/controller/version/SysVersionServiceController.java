package com.center.medical.system.controller.version;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.system.bean.model.SysVersionService;
import com.center.medical.system.bean.param.SysVersionExecuteParam;
import com.center.medical.system.bean.param.SysVersionServiceParam;
import com.center.medical.system.service.SysVersionServiceService;
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
 * 版本控制-系统服务更新包记录(SysVersionService)接口
 *
 * @author makejava
 * @since 2024-03-01 18:03:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "版本控制-系统服务更新包记录")
@RequestMapping("sysVersionService")
public class SysVersionServiceController extends BaseController {
    /**
     * 服务对象
     */
    private final SysVersionServiceService sysVersionServiceService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple   分页参数
     * @param sysVersionService 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询版本控制-系统服务更新包记录")
    public R<IPage<SysVersionService>> getPage(PageParamSimple pageParamSimple, SysVersionService sysVersionService) {
        PageParam<SysVersionService> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sysVersionServiceService.getPage(page, sysVersionService));
    }

    /**
     * 查询版本控制-系统服务更新包记录列表
     *
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询列表", notes = "查询版本控制-系统服务更新包记录列表")
    public R<List<SysVersionService>> getList(SysVersionServiceParam param) {
        return R.ok(this.sysVersionServiceService.getList(param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查版本控制-系统服务更新包记录详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<SysVersionService> selectOne(@PathVariable Integer id) {
        return R.ok(this.sysVersionServiceService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param sysVersionService 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加版本控制-系统服务更新包记录")
    @Log(title = "版本控制-系统服务更新包记录", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sysVersionService.id"})
    public R insert(@RequestBody SysVersionService sysVersionService) {
        return R.toResult(this.sysVersionServiceService.save(sysVersionService));
    }

    /**
     * 修改数据
     *
     * @param sysVersionService 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新版本控制-系统服务更新包记录")
    @Log(title = "版本控制-系统服务更新包记录", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SysVersionService sysVersionService) {
        return R.toResult(this.sysVersionServiceService.updateById(sysVersionService));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除版本控制-系统服务更新包记录")
    @Log(title = "版本控制-系统服务更新包记录", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Integer> ids) {
        return R.toResult(this.sysVersionServiceService.removeByIds(ids));
    }

    /**
     * 执行更新
     *
     * @param param 执行参数
     * @return 新增结果
     */
    @PostMapping("/execute/{branchIds}")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "执行更新", notes = "执行更新")
    @Log(title = "版本控制-系统服务更新包记录-执行更新", businessType = BusinessType.OTHER)
    public R execute(SysVersionExecuteParam param) {
        return R.toResult(this.sysVersionServiceService.execute(param));
    }
}


