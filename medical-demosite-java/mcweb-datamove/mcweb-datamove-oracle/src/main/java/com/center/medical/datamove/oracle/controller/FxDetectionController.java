package com.center.medical.datamove.oracle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.oracle.bean.model.FxDetection;
import com.center.medical.datamove.oracle.service.FxDetectionService;
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
 * 本次体检异常结果检出统计(FxDetection)接口
 *
 * @author ay
 * @since 2023-07-18 09:18:48
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "本次体检异常结果检出统计")
@RequestMapping("fxDetection")
public class FxDetectionController extends BaseController {
    /**
     * 服务对象
     */
    private final FxDetectionService fxDetectionService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param fxDetection     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询本次体检异常结果检出统计")
    public R<IPage<FxDetection>> getPage(PageParamSimple pageParamSimple, FxDetection fxDetection) {
        PageParam<FxDetection> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.fxDetectionService.getPage(page, fxDetection));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查本次体检异常结果检出统计详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<FxDetection> selectOne(@PathVariable String id) {
        return R.ok(this.fxDetectionService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param fxDetection 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加本次体检异常结果检出统计")
    @Log(title = "本次体检异常结果检出统计", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"fxDetection.id"})
    public R insert(@RequestBody FxDetection fxDetection) {
        return R.toResult(this.fxDetectionService.save(fxDetection));
    }

    /**
     * 修改数据
     *
     * @param fxDetection 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新本次体检异常结果检出统计")
    @Log(title = "本次体检异常结果检出统计", businessType = BusinessType.UPDATE)
    public R update(@RequestBody FxDetection fxDetection) {
        return R.toResult(this.fxDetectionService.updateById(fxDetection));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除本次体检异常结果检出统计")
    @Log(title = "本次体检异常结果检出统计", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.fxDetectionService.removeByIds(ids));
    }
}

