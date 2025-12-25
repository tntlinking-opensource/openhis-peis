package com.center.medical.report.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.common.annotation.IpAuth;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.report.bean.param.ReportOpenApiGetByPatientcodeParam;
import com.center.medical.report.bean.param.ReportOpenApiPatientcodeListParam;
import com.center.medical.report.bean.vo.ReportOpenApiContentVo;
import com.center.medical.report.service.ReportOpenApiService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 报告外部接口
 * @author xhp
 * @since 2023-10-24 10:18
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 1)
@Api(tags = "报告外部接口")
@RequestMapping("/lan/report/pf")
@Validated
public class ReportOpenApiController extends BaseController {
    private final ReportOpenApiService reportOpenApiService;
    private final MapperFacade mapperFacade;
    private final PeispatientMapper peispatientMapper;

    @IpAuth
    @PostMapping("/patientcode/list")
    @ApiOperation(value = "获取体检号列表", notes = "根据时间段获取体检号列表")
    public R<IPage<String>> selectPatientcodeList(@RequestBody PageParamSimple pageParamSimple,@RequestBody ReportOpenApiPatientcodeListParam param){
        PageParam page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(reportOpenApiService.selectPatientcodeList(page,param));
    }

    @IpAuth
    @PostMapping("/report/getByPatientcode")
    @ApiOperation(value = "根据体检号获取报告数据", notes = "根据体检号获取报告数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号", required = true),
    })
    public R<List<ReportOpenApiContentVo>> getReportByPatientcode(@RequestBody @Validated ReportOpenApiGetByPatientcodeParam param){
        List<ReportContent> reportContentList=reportOpenApiService.list(
                new QueryWrapper<ReportContent>()
                    .eq("patientcode",param.getPatientcode())
                        .eq("report_type",5)
        );
        List<ReportOpenApiContentVo> reportOpenApiContentVos=BeanUtil.copyToList(reportContentList,ReportOpenApiContentVo.class);
        //去掉手机号身份证号
        //可能没有peispatient
        Peispatient peispatient=peispatientMapper.getByPatientCode(param.getPatientcode());
        if(peispatient!=null){
            String phone=peispatient.getPhone();
            String idcardno=peispatient.getIdcardno();
            for(ReportOpenApiContentVo reportOpenApiContentVo:reportOpenApiContentVos){
                String content=reportOpenApiContentVo.getContent();
                if(content!=null){
                    if(StrUtil.isNotEmpty(phone)){
                        content=content.replaceAll(phone,"");
                    }
                    if(StrUtil.isNotEmpty(idcardno)){
                        content=content.replaceAll(idcardno,"");
                    }
                    reportOpenApiContentVo.setContent(content);
                }
            }
        }
        return R.ok(reportOpenApiContentVos);
    }
}
