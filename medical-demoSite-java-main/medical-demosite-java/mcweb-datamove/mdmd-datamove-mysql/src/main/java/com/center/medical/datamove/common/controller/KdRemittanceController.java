package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.KdRemittance;
import com.center.medical.datamove.common.service.KdRemittanceService;
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
 * kingdeeremittance(KdRemittance)接口
 *
 * @author ay
 * @since 2023-07-17 20:45:04
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "kingdeeremittance")
@RequestMapping("kdRemittance")
public class KdRemittanceController extends BaseController {
    /**
     * 服务对象
     */
    private final KdRemittanceService kdRemittanceService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param kdRemittance    查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询kingdeeremittance")
    public R<IPage<KdRemittance>> getPage(PageParamSimple pageParamSimple, KdRemittance kdRemittance) {
        PageParam<KdRemittance> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.kdRemittanceService.getPage(page, kdRemittance));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param fid 主键
     * @return 单条数据
     */
    @GetMapping("{fid}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据fid查kingdeeremittance详情")
    @ApiImplicitParam(name = "fid", value = "要查询的对象的主键{fid}")
    public R<KdRemittance> selectOne(@PathVariable String fid) {
        return R.ok(this.kdRemittanceService.getInfoById(fid));
    }

    /**
     * 新增数据
     *
     * @param kdRemittance 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加kingdeeremittance")
    @Log(title = "kingdeeremittance", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"kdRemittance.fid"})
    public R insert(@RequestBody KdRemittance kdRemittance) {
        return R.toResult(this.kdRemittanceService.save(kdRemittance));
    }

    /**
     * 修改数据
     *
     * @param kdRemittance 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新kingdeeremittance")
    @Log(title = "kingdeeremittance", businessType = BusinessType.UPDATE)
    public R update(@RequestBody KdRemittance kdRemittance) {
        return R.toResult(this.kdRemittanceService.updateById(kdRemittance));
    }

    /**
     * 删除数据
     *
     * @param fids 删除的对象主键{fid}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除kingdeeremittance")
    @Log(title = "kingdeeremittance", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{fid}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> fids) {
        return R.toResult(this.kdRemittanceService.removeByIds(fids));
    }
}

