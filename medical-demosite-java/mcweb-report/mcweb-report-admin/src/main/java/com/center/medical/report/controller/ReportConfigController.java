package com.center.medical.report.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.report.bean.model.ReportConfig;
import com.center.medical.report.bean.vo.ReportConfigVo;
import com.center.medical.report.service.ReportConfigService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 报告配置(ReportConfig)接口
 *
 * @author ay
 * @since 2023-07-25 10:00:30
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "报告配置")
@RequestMapping("/report/reportConfig")
public class ReportConfigController extends BaseController {
    /**
     * 服务对象
     */
    private final ReportConfigService reportConfigService;
    private final MapperFacade mapperFacade;


    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param reportConfig    查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询报告配置")
    public R<IPage<ReportConfig>> getPage(PageParamSimple pageParamSimple, ReportConfig reportConfig) {
        PageParam<ReportConfig> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reportConfigService.getPage(page, reportConfig));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param branchId 分中心id
     * @return 单条数据
     */
    @GetMapping("/getBranchConfig")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取分中心报告配置", notes = "获取分中心报告配置")
    @ApiImplicitParam(name = "branchId", value = "分中心id")
    public R<ReportConfigVo> getBranchConfig(String branchId) {
        String content = reportConfigService.getBranchConfig(branchId);
        ReportConfigVo vo = StringUtils.isNotEmpty(content) ? JSONObject.parseObject(content, ReportConfigVo.class) : null;
        return R.ok(vo);
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查报告配置详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<ReportConfig> selectOne(@PathVariable String id) {
        return R.ok(this.reportConfigService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param reportConfig 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加报告配置")
    @Log(title = "报告配置", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"reportConfig.id"})
    public R insert(@RequestBody ReportConfig reportConfig) {
        reportConfig.setCreatedate(new Date());
        reportConfig.setIsDefault(0);
        return R.toResult(this.reportConfigService.save(reportConfig));
    }

    /**
     * 修改数据
     *
     * @param reportConfig 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新报告配置")
    @Log(title = "报告配置", businessType = BusinessType.UPDATE)
    public R update(@RequestBody ReportConfig reportConfig) {
        reportConfig.setModifydate(new Date());
        return R.toResult(this.reportConfigService.updateById(reportConfig));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除报告配置")
    @Log(title = "报告配置", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        ReportConfig reportConfig = new ReportConfig();
        reportConfig.setModifydate(new Date());
        reportConfig.setStatus("1");
        return R.toResult(this.reportConfigService.update(reportConfig,new LambdaQueryWrapper<ReportConfig>().in(ReportConfig::getId,ids)));
    }

}

