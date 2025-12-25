package com.center.medical.appadmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.SysConfig;
import com.center.medical.appadmin.bean.param.ConfigPageParam;
import com.center.medical.appadmin.service.AppConfigService;
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
 * 系统配置信息表(SysConfig)接口
 *
 * @author ay
 * @since 2024-03-19 17:40:42
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "系统配置")
@RequestMapping("app/sysConfig")
public class AppConfigController extends BaseController {
    /**
     * 服务对象
     */
    private final AppConfigService appConfigService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param       查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询系统配置信息表")
    public R<IPage<SysConfig>> getPage(PageParamSimple pageParamSimple, ConfigPageParam param) {
        PageParam<SysConfig> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.appConfigService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查系统配置信息表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<SysConfig> selectOne(@PathVariable Long id) {
        return R.ok(this.appConfigService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param sysConfig 实体对象
     * @return 新增结果
     */
    @PostMapping("/saOrUp")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加或修改", notes = "添加系统配置信息表")
    @Log(title = "系统配置信息表", businessType = BusinessType.INSERT)
    public R insert(@RequestBody SysConfig sysConfig) {
        return R.toResult(this.appConfigService.saOrUp(sysConfig));
    }



    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除系统配置信息表")
    @Log(title = "系统配置信息表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Long> ids) {
        return R.toResult(this.appConfigService.deleteByIds(ids));
    }

}
