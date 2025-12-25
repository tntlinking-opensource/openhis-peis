package com.center.medical.datamove.oracle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.oracle.bean.model.AppReservationGroup;
import com.center.medical.datamove.oracle.service.AppReservationGroupService;
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
 * 小程序团检预约人数(AppReservationGroup)接口
 *
 * @author ay
 * @since 2023-07-18 09:11:54
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "小程序团检预约人数")
@RequestMapping("appReservationGroup")
public class AppReservationGroupController extends BaseController {
    /**
     * 服务对象
     */
    private final AppReservationGroupService appReservationGroupService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple     分页参数
     * @param appReservationGroup 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询小程序团检预约人数")
    public R<IPage<AppReservationGroup>> getPage(PageParamSimple pageParamSimple, AppReservationGroup appReservationGroup) {
        PageParam<AppReservationGroup> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.appReservationGroupService.getPage(page, appReservationGroup));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查小程序团检预约人数详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<AppReservationGroup> selectOne(@PathVariable String id) {
        return R.ok(this.appReservationGroupService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param appReservationGroup 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加小程序团检预约人数")
    @Log(title = "小程序团检预约人数", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"appReservationGroup.id"})
    public R insert(@RequestBody AppReservationGroup appReservationGroup) {
        return R.toResult(this.appReservationGroupService.save(appReservationGroup));
    }

    /**
     * 修改数据
     *
     * @param appReservationGroup 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新小程序团检预约人数")
    @Log(title = "小程序团检预约人数", businessType = BusinessType.UPDATE)
    public R update(@RequestBody AppReservationGroup appReservationGroup) {
        return R.toResult(this.appReservationGroupService.updateById(appReservationGroup));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除小程序团检预约人数")
    @Log(title = "小程序团检预约人数", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.appReservationGroupService.removeByIds(ids));
    }
}

