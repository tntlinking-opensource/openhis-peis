package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.MdSyncDataType;
import com.center.medical.datamove.common.service.MdSyncDataTypeService;
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
 * 同步数据类型(MdSyncDataType)接口
 *
 * @author ay
 * @since 2023-07-17 20:48:52
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "同步数据类型")
@RequestMapping("mdSyncDataType")
public class MdSyncDataTypeController extends BaseController {
    /**
     * 服务对象
     */
    private final MdSyncDataTypeService mdSyncDataTypeService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param mdSyncDataType  查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询同步数据类型")
    public R<IPage<MdSyncDataType>> getPage(PageParamSimple pageParamSimple, MdSyncDataType mdSyncDataType) {
        PageParam<MdSyncDataType> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.mdSyncDataTypeService.getPage(page, mdSyncDataType));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param typeId 主键
     * @return 单条数据
     */
    @GetMapping("{typeId}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据typeId查同步数据类型详情")
    @ApiImplicitParam(name = "typeId", value = "要查询的对象的主键{typeId}")
    public R<MdSyncDataType> selectOne(@PathVariable Integer typeId) {
        return R.ok(this.mdSyncDataTypeService.getInfoById(typeId));
    }

    /**
     * 新增数据
     *
     * @param mdSyncDataType 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加同步数据类型")
    @Log(title = "同步数据类型", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"mdSyncDataType.typeId"})
    public R insert(@RequestBody MdSyncDataType mdSyncDataType) {
        return R.toResult(this.mdSyncDataTypeService.save(mdSyncDataType));
    }

    /**
     * 修改数据
     *
     * @param mdSyncDataType 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新同步数据类型")
    @Log(title = "同步数据类型", businessType = BusinessType.UPDATE)
    public R update(@RequestBody MdSyncDataType mdSyncDataType) {
        return R.toResult(this.mdSyncDataTypeService.updateById(mdSyncDataType));
    }

    /**
     * 删除数据
     *
     * @param typeIds 删除的对象主键{typeId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除同步数据类型")
    @Log(title = "同步数据类型", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{typeId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Integer> typeIds) {
        return R.toResult(this.mdSyncDataTypeService.removeByIds(typeIds));
    }
}

