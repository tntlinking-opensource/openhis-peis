package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sellcrm.bean.model.CreatecomboType;
import com.center.medical.sellcrm.service.CreatecomboTypeService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 最小套餐分类(CreatecomboType)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-15 10:47:37
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "最小套餐分类")
@RequestMapping("sell/createcomboType")
public class CreatecomboTypeController extends BaseController {
    /**
     * 服务对象
     */
    private final CreatecomboTypeService createcomboTypeService;
    private final MapperFacade mapperFacade;

    //TODO 权限设置

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param createcomboType 查询实体
     * @return 所有数据
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询最小套餐分类")
    public R<IPage<CreatecomboType>> selectAll(PageParamSimple pageParamSimple, CreatecomboType createcomboType) {
        PageParam<CreatecomboType> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.createcomboTypeService.getList(page, createcomboType));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查最小套餐分类详情")
    public R<CreatecomboType> selectOne(@PathVariable String id) {
        return R.ok(this.createcomboTypeService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param createcomboType 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加最小套餐分类")
    @Log(title = "最小套餐分类", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"createcomboType.id"})
    public R insert(@RequestBody CreatecomboType createcomboType) {
        return R.toResult(this.createcomboTypeService.save(createcomboType));
    }

    /**
     * 修改数据
     *
     * @param createcomboType 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新最小套餐分类")
    @Log(title = "最小套餐分类", businessType = BusinessType.UPDATE)
    public R update(@RequestBody CreatecomboType createcomboType) {
        return R.toResult(this.createcomboTypeService.updateById(createcomboType));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除最小套餐分类")
    @Log(title = "最小套餐分类", businessType = BusinessType.DELETE)
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.createcomboTypeService.removeByIds(ids));
    }
}

