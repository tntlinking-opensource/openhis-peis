package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.Questionnaire;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.report.service.QuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小程序问卷(Questionnaire)接口
 *
 * @author ay
 * @since 2023-12-09 15:11:28
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "小程序问卷")
@RequestMapping("/abteilung/questionnaire")
public class XcxQuestionnaireController extends BaseController {
    /**
     * 服务对象
     */
    private final QuestionnaireService questionnaireService;
    private final MapperFacade mapperFacade;



    /**
     * 新增数据
     *
     * @param patientcode 实体对象
     * @return 新增结果
     */
    @GetMapping("/getByCode")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "详情", notes = "根据id查小程序问卷详情")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<Questionnaire> getByCode(String patientcode) {
        String patientcode8 = patientcode.length()<=8?patientcode:patientcode.substring(patientcode.length()-8);
        Questionnaire questionnaire = questionnaireService.getOne(new LambdaQueryWrapper<Questionnaire>()
                .eq(Questionnaire::getPatientcode, patientcode8).eq(Questionnaire::getType,0));
        return R.ok(questionnaire);
    }



}
