package com.center.medical.synclc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sync.bean.model.SyncDataType;
import com.center.medical.sync.service.SyncDataTypeService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 同步数据类型(SyncDataType)表控制层
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:59
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "同步数据类型")
@RequestMapping("syncDataType/lc")
public class SyncDataTypeController extends BaseController {
    /**
     * 服务对象
     */
    private final SyncDataTypeService syncDataTypeService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param syncDataType    查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询同步数据类型")
    public R<IPage<SyncDataType>> getPage(PageParamSimple pageParamSimple, SyncDataType syncDataType) {
        PageParam<SyncDataType> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.syncDataTypeService.getPage(page, syncDataType));
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
    public R<SyncDataType> selectOne(@PathVariable Integer typeId) {
        return R.ok(this.syncDataTypeService.getInfoById(typeId));
    }

    /**
     * 新增数据
     *
     * @param syncDataType 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加同步数据类型")
    @Log(title = "同步数据类型", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"syncDataType.typeId"})
    public R insert(@RequestBody SyncDataType syncDataType) {
        return R.toResult(this.syncDataTypeService.save(syncDataType));
    }

    /**
     * 修改数据
     *
     * @param syncDataType 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新同步数据类型")
    @Log(title = "同步数据类型", businessType = BusinessType.UPDATE)
    public R update(@RequestBody SyncDataType syncDataType) {
        this.syncDataTypeService.updateById(syncDataType);

        this.syncDataTypeService.updateDiy(syncDataType);

        List<SyncDataType> list = new ArrayList<>();
        syncDataType.setTypeId(3);
        list.add(syncDataType);
        SyncDataType syncDataType1 = new SyncDataType();
        syncDataType1.setTypeId(4);
        syncDataType1.setTypeName("更新4");
        list.add(syncDataType1);

        return R.toResult(this.syncDataTypeService.updateBatchById(list));//R.toResult(this.syncDataTypeService.updateById(syncDataType));
    }

    /**
     * 删除数据
     *
     * @param typeIds 删除的对象主键{typeId}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{typeIds}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除同步数据类型")
    @Log(title = "同步数据类型-删除", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "typeIds", value = "要删除的对象主键{typeId}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<Integer> typeIds) {
        return R.toResult(this.syncDataTypeService.removeByIds(typeIds));
    }
}

