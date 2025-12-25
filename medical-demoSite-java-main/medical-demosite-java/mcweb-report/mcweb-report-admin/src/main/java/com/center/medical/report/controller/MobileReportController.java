package com.center.medical.report.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.center.medical.bean.dto.ReportListDto;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.bean.param.LastAccessParam;
import com.center.medical.bean.param.ValidationCodeParam;
import com.center.medical.bean.vo.ValidationCodeVo;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.report.bean.vo.ReportConfigVo;
import com.center.medical.report.service.MobileReportService;
import com.center.medical.report.service.ReportConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 手机版报告(ReportContent)接口
 *
 * @author ay
 * @since 2023-08-14 14:54:49
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "手机版报告")
@RequestMapping("/open/api/report/mobileReport")
public class MobileReportController {
    /**
     * 服务对象
     */
    private final MobileReportService mobileReportService;
    private final MapperFacade mapperFacade;
    private final ReportConfigService reportConfigService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询列表", notes = "查询列表")
    public R<List<ReportListDto>> getPage(String phone,String year) {
        return R.ok(this.mobileReportService.getReportList(phone,year));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("/details")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查生成报告内容详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R selectOne(String id) {
        ReportContent reportContent = mobileReportService.getInfoById(id);
        String content = reportContent.getContent();
        //json字符串转对象
        Object obj = JSON.parse(content);
        return R.ok(obj);
    }


    /**
     * 通过主键查询单条数据
     *
     * @param branchId 分中心id
     * @return 单条数据
     */
    @PostMapping("/getBranchConfig")
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
     * @param param
     * @return
     */
    @PostMapping("/validationCode")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "校验验证码并返回列表数据", notes = "校验验证码并返回列表数据")
    public R<ValidationCodeVo> validationCode (@RequestBody ValidationCodeParam param) {
        ValidationCodeVo vo = mobileReportService.validationCode(param);
        return R.ok(vo);
    }



    /**
     * 分享报告-访问次数和ip
     * @param param
     * @return
     */
    @PostMapping("/lastAccess")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "分享报告-访问次数和ip", notes = "分享报告-访问次数和ip")
    public R lastAccess (@RequestBody LastAccessParam param) {
        Boolean b = mobileReportService.lastAccess(param);
        return R.ok(b);
    }

}

