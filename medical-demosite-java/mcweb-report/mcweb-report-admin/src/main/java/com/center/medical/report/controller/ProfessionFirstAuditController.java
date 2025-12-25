package com.center.medical.report.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.report.bean.param.HealthAuditParam;
import com.center.medical.report.service.ProfessionFirstAuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 健康报告审核控制层
 *
 * @author 浮俊杰
 * @since 2022-12-19 20:37:35
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "报告管理-职业报告审核-职业初审")
@RequestMapping("/report/professionFirstAudit")
public class ProfessionFirstAuditController extends BaseController {
    /**
     * 服务对象
     */
    private final ProfessionFirstAuditService professionFirstAuditService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询职业一审页面数据
     *
     * @param pageParamSimple 分页参数
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/getListData")
    @ApiOperation(value = "分页查询职业一审页面数据", notes = "分页查询职业一审页面数据")
    public R<IPage<Peispatient>> getListData(PageParamSimple pageParamSimple, HealthAuditParam param) {
        PageParam<Peispatient> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.professionFirstAuditService.getListData(page, param));
    }

    /**
     * 职业一审页面数据通过
     *
     * @param ids     查询条件
     * @return 所有数据
     */
    @GetMapping("/pass/{ids}")
    @ApiOperation(value = "职业一审页面数据通过", notes = "职业一审页面数据通过")
    @ApiImplicitParam(name = "ids", value = "id序号")
    public R pass(@PathVariable String ids){
        return R.toResult(this.professionFirstAuditService.pass(ids));
    }

    /**
     * 职业一审页面数据反审
     *
     * @param ids     查询条件
     * @return 所有数据
     */
    @GetMapping("/unaudit/{ids}")
    @ApiOperation(value = "职业一审页面数据反审", notes = "职业一审页面数据反审")
    @ApiImplicitParam(name = "ids", value = "id序号")
    public R unaudit(@PathVariable String ids){
        return R.toResult(this.professionFirstAuditService.unaudit(ids));
    }

    /**
     * 职业一审页面数据不通过
     *
     * @param id, firstReason    查询条件
     * @return 所有数据
     */
    @GetMapping("/uncheck/{id}/{firstReason}")
    @ApiOperation(value = "职业一审页面数据不通过", notes = "职业一审页面数据不通过")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "id序号"),
        @ApiImplicitParam(name = "firstReason", value = "第一次审核原因")
    })
    public R uncheck(@PathVariable String id,@PathVariable String firstReason){
        return R.ok(this.professionFirstAuditService.uncheck(id,firstReason));
    }

    /**
     * 职业一审获取体检者数据
     *
     * @param patientcode     查询条件
     * @return 所有数据
     */
    @GetMapping("/getPatientData/{patientcode}")
    @ApiOperation(value = "职业一审获取体检者数据", notes = "职业一审获取体检者数据")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R getPatientData(@PathVariable String patientcode){
        return R.ok(this.professionFirstAuditService.getPatientData(patientcode));
    }

    /**
     * 批量通过职业一审
     *
     * @param peispatients 实体对象
     * @return 修改结果
     */
    @PutMapping("/batchpass/{peispatients}")
    @ApiOperation(value = "批量通过职业一审", notes = "批量通过职业一审")
    public R batchPass(@RequestBody List<Peispatient> peispatients){
        return R.ok(this.professionFirstAuditService.batchPass(peispatients));
    }

}

