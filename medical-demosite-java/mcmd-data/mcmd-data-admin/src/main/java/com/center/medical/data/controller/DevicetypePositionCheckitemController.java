package com.center.medical.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.DevicetypePositionCheckitem;
import com.center.medical.data.service.DevicetypePositionCheckitemService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 东软pacs部位方法基础表(DevicetypePositionCheckitem)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-17 21:50:28
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "东软pacs部位方法基础表")
@RequestMapping("devicetypePositionCheckitem")
public class DevicetypePositionCheckitemController extends BaseController {
    /**
     * 服务对象
     */
    private final DevicetypePositionCheckitemService devicetypePositionCheckitemService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询参数
     * @return 所有数据
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取部位检查项目数据", notes = "获取部位检查项目数据")
    public R<IPage<DevicetypePositionCheckitem>> selectAll(PageParamSimple pageParamSimple, DevicetypePositionCheckitem param) {
        PageParam<DevicetypePositionCheckitem> page = mapperFacade.map(pageParamSimple, PageParam.class);
        LambdaQueryWrapper<DevicetypePositionCheckitem> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(param.getId())) {
            wrapper.eq(DevicetypePositionCheckitem::getId, param.getId());
        }
        if (StringUtils.isNotBlank(param.getStudypositionname())) {
            wrapper.eq(DevicetypePositionCheckitem::getStudypositionname, param.getStudypositionname());
        }
        wrapper.orderByAsc(DevicetypePositionCheckitem::getStudypositionname);
        return R.ok(this.devicetypePositionCheckitemService.page(page, wrapper));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查东软pacs部位方法基础表详情")
    public R<DevicetypePositionCheckitem> selectOne(@PathVariable String id) {
        return R.ok(this.devicetypePositionCheckitemService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param devicetypePositionCheckitem 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加东软pacs部位方法基础表")
    @Log(title = "东软pacs部位方法基础表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"devicetypePositionCheckitem.id"})
    public R insert(@RequestBody DevicetypePositionCheckitem devicetypePositionCheckitem) {
        return R.toResult(this.devicetypePositionCheckitemService.save(devicetypePositionCheckitem));
    }

    /**
     * 修改数据
     *
     * @param devicetypePositionCheckitem 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新东软pacs部位方法基础表")
    @Log(title = "东软pacs部位方法基础表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody DevicetypePositionCheckitem devicetypePositionCheckitem) {
        return R.toResult(this.devicetypePositionCheckitemService.updateById(devicetypePositionCheckitem));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除东软pacs部位方法基础表")
    @Log(title = "东软pacs部位方法基础表", businessType = BusinessType.DELETE)
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.devicetypePositionCheckitemService.removeByIds(ids));
    }
}

