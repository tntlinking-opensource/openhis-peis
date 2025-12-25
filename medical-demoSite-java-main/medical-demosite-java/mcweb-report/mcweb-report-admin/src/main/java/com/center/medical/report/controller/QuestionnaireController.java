package com.center.medical.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.Questionnaire;
import com.center.medical.bean.param.QuestionnaireParam;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.report.service.QuestionnaireService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 小程序问卷(Questionnaire)接口
 *
 * @author ay
 * @since 2023-12-05 08:57:21
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "小程序问卷")
@RequestMapping("/open/api/questionnaire")
public class QuestionnaireController extends BaseController {
    /**
     * 服务对象
     */
    private final QuestionnaireService questionnaireService;
    private final MapperFacade mapperFacade;



    /**
     * 新增数据
     *
     * @param questionnaire 实体对象
     * @return 新增结果
     */
    @PostMapping("/saOrUp")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加或更新", notes = "添加或更新")
    @Log(title = "小程序问卷", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"questionnaire.id"})
    public R insert(Questionnaire questionnaire) {
        boolean b = questionnaireService.saveOrUpdate(questionnaire);
        return R.ok(questionnaire.getId());
    }




    /**
     * 获取历史问卷
     *
     * @param phone 实体对象
     * @return 新增结果
     */
    @PostMapping("/getHistory")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "获取历史问卷", notes = "获取历史问卷")
    public R getHistory(String phone) {
        List<Questionnaire> list = questionnaireService.list(new LambdaQueryWrapper<Questionnaire>()
                .eq(Questionnaire::getPhone, phone)
                .orderByDesc(Questionnaire::getCreatedate));
        return R.ok(list);
    }




    /**
     * 获取历史问卷
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/details")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "获取详情", notes = "获取详情")
    public R details(QuestionnaireParam param) {
        Questionnaire questionnaire = questionnaireService.getOne(new LambdaQueryWrapper<Questionnaire>()
                .eq(Questionnaire::getPhone, param.getPhone())
                .eq(Questionnaire::getPatientcode,param.getPatientcode())
        );
        return R.ok(questionnaire);
    }

}
