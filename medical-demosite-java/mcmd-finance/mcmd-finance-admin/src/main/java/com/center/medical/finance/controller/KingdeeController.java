package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.finance.bean.param.KingdeeRemittanceParam;
import com.center.medical.finance.bean.vo.KdCustomerVo;
import com.center.medical.finance.constant.KingdeeConstants;
import com.center.medical.finance.service.*;
import com.center.medical.finance.service.*;
import com.center.medical.system.service.KdDepartmentService;
import com.center.medical.system.service.KdSaleerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author xhp
 * @since 2023-05-15 8:04
 */
@RestController
@RequestMapping("finance/kingdee")
@Api(tags = "金蝶")
@RequiredArgsConstructor
@Validated
public class KingdeeController extends BaseController {
    private final KdOrganizationService kdOrganizationService;
    private final KdDepartmentService kdDepartmentService;
    private final KdReserService kdReserService;
    private final KdRemittanceService kdRemittanceService;
    private final KdCustomerService kdCustomerService;
    private final KdPaywayService kdPaywayService;
    private final KdSaleerService kdSaleerService;
    private final KdUploadService kdUploadService;

    private final MapperFacade mapperFacade;

    @PostMapping("/upgradeKingdeeOrganization")
    @ApiOperation(value = "其它中心编码更新", notes = "其它中心编码更新、金蝶组织更新")
    public R upgradeKingdeeOrganization() {
        kdOrganizationService.upgradeKingdeeOrganization();
        return R.ok();
    }

    @PostMapping("/upgradeOtherPayable")
    @ApiOperation(value = "上传银行流水", notes = "上传银行流水")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "transactionNumber", value = "流水号", required = true),
    })
    public R upgradeOtherPayable(@RequestParam String transactionNumber) {
        kdReserService.upgradeOtherPayable(transactionNumber);
        return R.ok();
    }

    @PostMapping("/upgradeDepartmentByKingdee")
    @ApiOperation(value = "部门编码更新", notes = "部门编码更新")
    public R upgradeDepartmentByKingdee() {
        kdDepartmentService.upgradeDepartmentByKingdee();
        return R.ok();
    }

    @PostMapping("/upgradeKingdeeRemittanceByKingdee")
    @ApiOperation(value = "银行交易流水更新", notes = "银行交易流水更新")
    public R upgradeKingdeeRemittanceByKingdee(@RequestBody KingdeeRemittanceParam param) {
        kdRemittanceService.upgradeKingdeeRemittanceByKingdee(param);
        return R.ok();
    }

    @PostMapping("/upgradeIdentityByKingdee")
    @ApiOperation(value = "upgradeIdentityByKingdee", notes = "客户升级时调用,获取金蝶系统客户")
    public R<String> upgradeIdentityByKingdee() {
        return R.ok(kdCustomerService.upgradeIdentityByKingdee());
    }

    @PostMapping("/upgradeKingdeePayWay")
    @ApiOperation(value = "收费方式-金蝶数据更新", notes = "收费方式-金蝶数据更新")
    public R upgradeKingdeePayWay() {
        kdPaywayService.upgradeKingdeePayWay();
        return R.ok();
    }

    @PostMapping("/upgradeSaleer")
    @ApiOperation(value = "用户-金蝶数据更新", notes = "用户-金蝶数据更新")
    public R upgradeSaleer() {
        kdSaleerService.upgradeSaleer();
        return R.ok();
    }

    @PostMapping("/upgradeCustomer")
    @ApiOperation(value = "金蝶客户数据更新", notes = "金蝶客户数据更新")
    public R upgradeCustomer() {
        String branchId = SecurityUtils.getCId();
        new Thread(() -> kdCustomerService.upgradeCustomer(branchId)).start();
        return R.ok(KingdeeConstants.MSG_UPDATING);
    }

    @PostMapping("/taskAboutUpdateT")
    @ApiOperation(value = "上传个检费用", notes = "上传个检费用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, example = "yyyy-MM-dd"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, example = "yyyy-MM-dd"),
    })
    public R<String> taskAboutUpdateT(@RequestParam String startTime, @RequestParam String endTime) {
        String result = kdUploadService.taskAboutUpdateT(startTime, endTime);
        return R.ok(result);
    }

    @PostMapping("/taskAboutUpdateG")
    @ApiOperation(value = "上传团检费用", notes = "上传团检费用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, example = "yyyy-MM-dd"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, example = "yyyy-MM-dd"),
    })
    public R<String> taskAboutUpdateG(@RequestParam String startTime, @RequestParam String endTime) {
        String result = kdUploadService.taskAboutUpdateG(startTime, endTime);
        return R.ok(result);
    }

    @PostMapping("/updateSettlement")
    @ApiOperation(value = "上传个检结算", notes = "上传个检结算")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, example = "yyyy-MM-dd"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, example = "yyyy-MM-dd"),
    })
    public R<String> updateSettlement(@RequestParam String startTime, @RequestParam String endTime) {
        String result = kdUploadService.updateSettlement(startTime, endTime);
        return R.ok(result);
    }

    @PostMapping("/updateGroupSettlement")
    @ApiOperation(value = "上传团体结算", notes = "上传团体结算")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, example = "yyyy-MM-dd"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, example = "yyyy-MM-dd"),
    })
    public R<String> updateGroupSettlement(@RequestParam String startTime, @RequestParam String endTime) {
        String result = kdUploadService.updateGroupSettlement(startTime, endTime);
        return R.ok(result);
    }

    @PostMapping("/updateMonth")
    @ApiOperation(value = "积分和体检卡月度个检结算", notes = "积分和体检卡月度个检结算")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, example = "yyyy-MM-dd"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, example = "yyyy-MM-dd"),
    })
    public R<String> updateMonth(@RequestParam String startTime, @RequestParam String endTime) {
        String result = kdUploadService.updateMonth(startTime, endTime);
        return R.ok(result);
    }

    @PostMapping("/updateMonthGroup")
    @ApiOperation(value = "积分和体检卡月度团体结算", notes = "积分和体检卡月度团体结算")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, example = "yyyy-MM-dd"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, example = "yyyy-MM-dd"),
    })
    public R<String> updateMonthGroup(@RequestParam String startTime, @RequestParam String endTime) {
        String result = kdUploadService.updateMonthGroup(startTime, endTime);
        return R.ok(result);
    }

    @PostMapping("/updateSettlementOfOrg")
    @ApiOperation(value = "上传统收", notes = "上传统收")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, example = "yyyy-MM-dd"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, example = "yyyy-MM-dd"),
    })
    public R<String> updateSettlementOfOrg(@RequestParam String startTime, @RequestParam String endTime) {
        String result = kdUploadService.updateSettlementOfOrg(startTime, endTime);
        return R.ok(result);
    }

    @PostMapping("/checkOrgKingdeeName")
    @ApiOperation(value = "检验统收团体金蝶名", notes = "检验统收团体金蝶名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = true, example = "yyyy-MM-dd"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = true, example = "yyyy-MM-dd"),
    })
    public R<String> checkOrgKingdeeName(@RequestParam String startTime, @RequestParam String endTime) {
        String result = kdUploadService.checkOrgKingdeeName(startTime, endTime);
        return R.ok(result);
    }

    @PostMapping("/checkOrgName")
    @ApiOperation(value = "检验匹配团体金蝶名", notes = "检验匹配团体金蝶名")
    public R<String> checkOrgName() {
        String result = kdUploadService.checkOrgName();
        return R.ok(result);
    }


    @GetMapping("/getKingdeeCustomerData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取金蝶客户", notes = "获取金蝶客户")
    public R<IPage<KdCustomerVo>> getKingdeeCustomerData(PageParamSimple pageParamSimple, String key) {
        PageParam<KdCustomerVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(kdCustomerService.getKingdeeCustomerData(page, key));
    }

}
