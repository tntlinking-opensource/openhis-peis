package com.center.medical.datamove.oracle.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.oracle.bean.model.FailTotalVisit;
import com.center.medical.datamove.oracle.service.FailTotalVisitService;
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
 * KF迟捡、阳性、不合格样本回访(FailTotalVisit)接口
 *
 * @author ay
 * @since 2023-07-18 09:18:40
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "KF迟捡、阳性、不合格样本回访")
@RequestMapping("failTotalVisit")
public class FailTotalVisitController extends BaseController {
    /**
     * 服务对象
     */
    private final FailTotalVisitService failTotalVisitService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param failTotalVisit  查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询KF迟捡、阳性、不合格样本回访")
    public R<IPage<FailTotalVisit>> getPage(PageParamSimple pageParamSimple, FailTotalVisit failTotalVisit) {
        PageParam<FailTotalVisit> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.failTotalVisitService.getPage(page, failTotalVisit));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查KF迟捡、阳性、不合格样本回访详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<FailTotalVisit> selectOne(@PathVariable String id) {
        return R.ok(this.failTotalVisitService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param failTotalVisit 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加KF迟捡、阳性、不合格样本回访")
    @Log(title = "KF迟捡、阳性、不合格样本回访", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"failTotalVisit.id"})
    public R insert(@RequestBody FailTotalVisit failTotalVisit) {
        return R.toResult(this.failTotalVisitService.save(failTotalVisit));
    }

    /**
     * 修改数据
     *
     * @param failTotalVisit 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新KF迟捡、阳性、不合格样本回访")
    @Log(title = "KF迟捡、阳性、不合格样本回访", businessType = BusinessType.UPDATE)
    public R update(@RequestBody FailTotalVisit failTotalVisit) {
        return R.toResult(this.failTotalVisitService.updateById(failTotalVisit));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除KF迟捡、阳性、不合格样本回访")
    @Log(title = "KF迟捡、阳性、不合格样本回访", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.failTotalVisitService.removeByIds(ids));
    }
}

