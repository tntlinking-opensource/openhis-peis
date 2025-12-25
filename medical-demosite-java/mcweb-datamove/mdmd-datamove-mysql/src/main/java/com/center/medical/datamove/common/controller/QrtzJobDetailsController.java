package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.QrtzJobDetails;
import com.center.medical.datamove.common.service.QrtzJobDetailsService;
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
 * 任务详细信息表(QrtzJobDetails)接口
 *
 * @author ay
 * @since 2023-07-17 20:54:30
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "任务详细信息表")
@RequestMapping("qrtzJobDetails")
public class QrtzJobDetailsController extends BaseController {
    /**
     * 服务对象
     */
    private final QrtzJobDetailsService qrtzJobDetailsService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param qrtzJobDetails  查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询任务详细信息表")
    public R<IPage<QrtzJobDetails>> getPage(PageParamSimple pageParamSimple, QrtzJobDetails qrtzJobDetails) {
        PageParam<QrtzJobDetails> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.qrtzJobDetailsService.getPage(page, qrtzJobDetails));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param schedName 主键
     * @return 单条数据
     */
    @GetMapping("{schedName}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据schedName查任务详细信息表详情")
    @ApiImplicitParam(name = "schedName", value = "要查询的对象的主键{schedName}")
    public R<QrtzJobDetails> selectOne(@PathVariable String schedName) {
        return R.ok(this.qrtzJobDetailsService.getInfoById(schedName));
    }

    /**
     * 新增数据
     *
     * @param qrtzJobDetails 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加任务详细信息表")
    @Log(title = "任务详细信息表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"qrtzJobDetails.schedName"})
    public R insert(@RequestBody QrtzJobDetails qrtzJobDetails) {
        return R.toResult(this.qrtzJobDetailsService.save(qrtzJobDetails));
    }

    /**
     * 修改数据
     *
     * @param qrtzJobDetails 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新任务详细信息表")
    @Log(title = "任务详细信息表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody QrtzJobDetails qrtzJobDetails) {
        return R.toResult(this.qrtzJobDetailsService.updateById(qrtzJobDetails));
    }

    /**
     * 删除数据
     *
     * @param schedNames 删除的对象主键{schedName}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除任务详细信息表")
    @Log(title = "任务详细信息表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{schedName}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> schedNames) {
        return R.toResult(this.qrtzJobDetailsService.removeByIds(schedNames));
    }
}

