package com.center.medical.abteilung.controller;

import com.center.medical.abteilung.bean.model.PeisQuestionnaireSecond;
import com.center.medical.abteilung.service.PeisQuestionnaireSecondService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.service.PeispatientService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 科室列表-健康体检问卷(PeisQuestionnaireSecond)表控制层
 *
 * @author ay
 * @since 2023-02-17 08:46:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "科室列表-健康体检问卷")
@RequestMapping("abteilung/peisQuestionnaireSecond")
public class PeisQuestionnaireSecondController extends BaseController {
    /**
     * 服务对象
     */
    private final PeisQuestionnaireSecondService peisQuestionnaireSecondService;
    private final MapperFacade mapperFacade;
    private final PeispatientService peispatientService;


    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("/search")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "查询", notes = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "ksId", value = "科室id")
    })
    public R selectOne(String patientCode, String ksId) {
        return R.ok(this.peisQuestionnaireSecondService.search(patientCode, ksId));
    }

    /**
     * 添加健康体检问卷
     *
     * @param peisQuestionnaireSecond 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加健康体检问卷")
    @Log(title = "健康体检问卷", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peisQuestionnaireSecond.id"})
    public R insert(@RequestBody PeisQuestionnaireSecond peisQuestionnaireSecond) {
        return R.toResult(this.peisQuestionnaireSecondService.saOrUp(peisQuestionnaireSecond));
    }


    /**
     * 辅助功能-问卷
     *
     * @param patientCode
     * @return
     */
    @GetMapping("/view")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "辅助功能-问卷", notes = "辅助功能-问卷")
    @ApiImplicitParam(name = "patientCode", value = "体检号")
    public R view(String patientCode) {
        Peispatient patient = peispatientService.getByPatientCode(patientCode);
        //健康知识
        List<String> des = new ArrayList<>();
        String knowledge = patient.getKnowledge();
        if (StringUtils.isNotEmpty(knowledge)) {
            des = Arrays.asList(knowledge.split("@@"));
        } else {
            des.add("未填写过问卷。");
        }
        return R.ok(des);
    }
}

