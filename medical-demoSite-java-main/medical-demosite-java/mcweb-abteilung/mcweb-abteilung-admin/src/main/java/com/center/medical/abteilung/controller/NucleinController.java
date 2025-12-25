package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.model.Nuclein;
import com.center.medical.abteilung.bean.param.NucleinParam;
import com.center.medical.abteilung.service.NucleinService;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 核酸检测报告上传记录(Nuclein)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-04 20:38:20
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "核酸检测报告上传记录")
@RequestMapping("nuclein")
public class NucleinController extends BaseController {
    /**
     * 服务对象
     */
    private final NucleinService nucleinService;

    /**
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询核酸检测报告上传记录")
    public R<IPage<Nuclein>> selectAll(PageParam<Nuclein> page, NucleinParam param) {
        return R.ok(this.nucleinService.getList(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查核酸检测报告上传记录详情")
    public R<Nuclein> selectOne(@PathVariable String id) {
        return R.ok(this.nucleinService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param nuclein 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加核酸检测报告上传记录")
    @Log(title = "核酸检测报告上传记录", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"nuclein.id"})
    public R insert(@RequestBody Nuclein nuclein) {
        nuclein.setId("1223");

        return R.toResult(this.nucleinService.save(nuclein));
    }

    /**
     * 修改数据
     *
     * @param nuclein 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::update')")
    @ApiOperation(value = "更新", notes = "更新核酸检测报告上传记录")
    @Log(title = "核酸检测报告上传记录", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Nuclein nuclein) {
        return R.toResult(this.nucleinService.updateById(nuclein));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::del')")
    @ApiOperation(value = "删除", notes = "删除核酸检测报告上传记录")
    @Log(title = "核酸检测报告上传记录", businessType = BusinessType.DELETE)
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.nucleinService.removeByIds(ids));
    }
}

