package com.center.medical.report.controller;

import com.alibaba.fastjson.JSON;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.bean.param.ReportContentParam;
import com.center.medical.report.bean.param.ReportContentUpParam;
import com.center.medical.service.ReportContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 生成报告内容(ReportContent)表控制层
 *
 * @author ay
 * @since 2023-05-18 10:43:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "报告生成内容")
@RequestMapping("/report/reportContent")
public class ReportContentController extends BaseController {
    /**
     * 服务对象
     */
    private final ReportContentService reportContentService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param param   查询条件
     * @return 所有数据
     */
    @GetMapping("/getData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询报告内容", notes = "查询报告内容")
    public R getData(ReportContentParam param) {
        ReportContent reportContent = reportContentService.findReport(param);
        if (ObjectUtils.isEmpty(reportContent)){
            throw new ServiceException("请先生成报告后再操作!");
        }
        String content = reportContent.getContent();
        //json字符串转对象
        Object obj = JSON.parse(content);
        return R.ok(obj);
    }



    /**
     * 修改数据
     *
     * @param param 实体对象
     * @return 修改结果
     */
    @PutMapping("/update")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新报告生成内容")
    @Log(title = "更新报告生成内容", businessType = BusinessType.UPDATE)
    public R update(@RequestBody ReportContentUpParam param) {
        //只有团检报告和对比报告可以修改
        if (param.getReportType() != 4 && param.getReportType() != 6 &&  param.getReportType() != 8){
            throw new ServiceException("该报告不可以修改!");
        }
        ReportContentParam rcParam = mapperFacade.map(param, ReportContentParam.class);
        ReportContent reportContent = reportContentService.findReport(rcParam);
        if (ObjectUtils.isEmpty(reportContent)){
            throw new ServiceException("请先生成报告!");
        }
        //已审核不能修改
        if (reportContent.getCheckStatus() == 1){
            throw new ServiceException("已主检审核,不能再修改!");
        }
        //更新操作
        String json = JSON.toJSONString(param.getContent());
        reportContent.setContent(json);
        reportContent.setUpdateBy(SecurityUtils.getUserNo());
        return R.toResult(this.reportContentService.updateById(reportContent));
    }





}

