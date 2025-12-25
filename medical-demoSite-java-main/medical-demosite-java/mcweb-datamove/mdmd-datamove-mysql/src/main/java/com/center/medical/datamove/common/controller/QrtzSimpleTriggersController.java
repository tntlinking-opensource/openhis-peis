package com.center.medical.datamove.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.datamove.common.bean.model.QrtzSimpleTriggers;
import com.center.medical.datamove.common.service.QrtzSimpleTriggersService;
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
 * 简单触发器的信息表(QrtzSimpleTriggers)接口
 *
 * @author ay
 * @since 2023-07-17 20:54:31
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "简单触发器的信息表")
@RequestMapping("qrtzSimpleTriggers")
public class QrtzSimpleTriggersController extends BaseController {
    /**
     * 服务对象
     */
    private final QrtzSimpleTriggersService qrtzSimpleTriggersService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple    分页参数
     * @param qrtzSimpleTriggers 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询简单触发器的信息表")
    public R<IPage<QrtzSimpleTriggers>> getPage(PageParamSimple pageParamSimple, QrtzSimpleTriggers qrtzSimpleTriggers) {
        PageParam<QrtzSimpleTriggers> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.qrtzSimpleTriggersService.getPage(page, qrtzSimpleTriggers));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param schedName 主键
     * @return 单条数据
     */
    @GetMapping("{schedName}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据schedName查简单触发器的信息表详情")
    @ApiImplicitParam(name = "schedName", value = "要查询的对象的主键{schedName}")
    public R<QrtzSimpleTriggers> selectOne(@PathVariable String schedName) {
        return R.ok(this.qrtzSimpleTriggersService.getInfoById(schedName));
    }

    /**
     * 新增数据
     *
     * @param qrtzSimpleTriggers 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加简单触发器的信息表")
    @Log(title = "简单触发器的信息表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"qrtzSimpleTriggers.schedName"})
    public R insert(@RequestBody QrtzSimpleTriggers qrtzSimpleTriggers) {
        return R.toResult(this.qrtzSimpleTriggersService.save(qrtzSimpleTriggers));
    }

    /**
     * 修改数据
     *
     * @param qrtzSimpleTriggers 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新简单触发器的信息表")
    @Log(title = "简单触发器的信息表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody QrtzSimpleTriggers qrtzSimpleTriggers) {
        return R.toResult(this.qrtzSimpleTriggersService.updateById(qrtzSimpleTriggers));
    }

    /**
     * 删除数据
     *
     * @param schedNames 删除的对象主键{schedName}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除简单触发器的信息表")
    @Log(title = "简单触发器的信息表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{schedName}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> schedNames) {
        return R.toResult(this.qrtzSimpleTriggersService.removeByIds(schedNames));
    }
}

