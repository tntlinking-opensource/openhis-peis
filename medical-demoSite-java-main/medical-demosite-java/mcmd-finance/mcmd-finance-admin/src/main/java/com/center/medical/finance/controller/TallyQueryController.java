package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.dto.PayResultDto;
import com.center.medical.bean.model.PeisReserPayway;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.PeispatientReservationCharge;
import com.center.medical.bean.param.PayParam;
import com.center.medical.bean.param.RefundParam;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.finance.bean.param.TQPageParam;
import com.center.medical.finance.bean.param.TQSaOrUpParam;
import com.center.medical.finance.bean.param.TallyQueryPayParam;
import com.center.medical.finance.bean.vo.RemitterVo;
import com.center.medical.finance.bean.vo.TQPageVo;
import com.center.medical.finance.service.TallyQueryService;
import com.center.medical.service.PeisReserPaywayService;
import com.center.medical.service.PeispatientReservationChargeService;
import com.center.medical.service.PeispatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 记账管理-记账结算(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-03-31 14:27:31
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "记账管理-记账结算和记账查询")
@RequestMapping("finance/tallyQuery")
public class TallyQueryController extends BaseController {
    /**
     * 服务对象
     */
    private final TallyQueryService tallyQueryService;
    private final MapperFacade mapperFacade;
    private final PeisReserPaywayService peisReserPaywayService;
    private final PeispatientReservationChargeService peispatientReservationChargeService;
    private final PeispatientService peispatientService;

    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【记账管理-记账结算】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取分中心信息", "GET", "/sell/createorder/getBranchData", "财务管理->体检卡管理-卡充值->获取分中心信息", null),
                new InterfaceVo("获取全部支付方式", "GET", "/dictpayway/getPayWayData", "07.会员系统->客服管理-家庭会员-家庭卡消费->获取全部支付方式", null),
                new InterfaceVo("领取人信息", "GET", "/finance/sendCard/getLqrData", "07.会员系统->客服管理-家庭会员-家庭卡消费->领取人信息", null)
        );
        return R.ok(new FunctionVo("09.财务管理", "记账管理-记账结算", interfaceVos, "09.财务管理"));
    }


    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "里面有个字段jzhj，就是jzje的合计数值")
    public R<IPage<TQPageVo>> getPage(PageParamSimple pageParamSimple, TQPageParam param) {
        PageParam<TQPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.tallyQueryService.getList(page, param));
    }


    /**
     * 记帐查询数据导出
     *
     * @param response
     * @param param
     */
    @Log(title = "记帐信息列表", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "记帐查询数据导出", notes = "记帐查询数据导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, TQPageParam param) {
        List<TQPageVo> list = tallyQueryService.getExportData(param);
        ExcelUtil<TQPageVo> util = new ExcelUtil<TQPageVo>(TQPageVo.class);
        util.exportExcel(response, list, "记帐信息列表");
    }


    /**
     * 结算展现页数据
     *
     * @param id
     * @return
     */
    @GetMapping("/refund")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "结算展现页数据", notes = "结算展现页数据")
    @ApiImplicitParam(name = "id", value = "id")
    public R<PeispatientReservationCharge> refund(String id) {
        //体检者结算表
        PeispatientReservationCharge charge = peispatientReservationChargeService.getInfoById(id);
        //体检者表
        Peispatient peispatient = peispatientService.getByPatientCode(charge.getPatientcode());
        if (ObjectUtils.isNotEmpty(peispatient)) {
            charge.setName(peispatient.getPatientname());
            charge.setOrgName(peispatient.getOrgName());
        }
        return R.ok(charge);
    }


    /**
     * 获取结算信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getBillingData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取结算信息", notes = "不用传patientCode，只传id就可以了")
    @ApiImplicitParam(name = "id", value = "id")
    public R<List<PeisReserPayway>> getBillingData(String id) {
        //体检者结算方式表
        List<PeisReserPayway> list = peisReserPaywayService.getBillingData(id);
        return R.ok(list);
    }


    /**
     * 卡扣款
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdateFee")
//    @PreAuthorize("@ss.hasPermi('reception:registration:charge')")
    @ApiOperation(value = "结算-卡扣款", notes = "结算-卡扣款")
    @Log(title = "记账管理-结算-卡扣款", businessType = BusinessType.INSERT)
    public R saveOrUpdateFee(@RequestBody PayParam param) {
        //TODO wait 实现数据库锁，保证支付信息的一致性
        log.info("记账管理-结算-卡扣款：{}", param);
        return R.toResult(tallyQueryService.saveOrUpdateFee(param));
    }

    /**
     * 退款：体检卡、会员卡等
     *
     * @param param 退款参数
     * @return
     */
    @PostMapping("/refund")
//    @PreAuthorize("@ss.hasPermi('reception:registration:charge')")
    @ApiOperation(value = "退款", notes = "退款：各种收费方式等")
    @Log(title = "记账管理-收费管理-退款", businessType = BusinessType.INSERT)
    public R<Boolean> refund(@RequestBody RefundParam param) {
        //TODO wait 实现数据库锁，保证支付信息的一致性
        log.info("记账管理-收费管理-退款：{}", param);
        return R.ok(this.tallyQueryService.refund(param));
    }


    /**
     * 结算-保存
     *
     * @param param
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "结算-保存", notes = "结算-保存")
    public R update(@RequestBody TQSaOrUpParam param) {
        Boolean b = tallyQueryService.saOrUp(param, true);
        return R.toResult(b);
    }


    /**
     * 汇款单位名单
     * @param key
     * @return
     */
    @GetMapping("/getRemitter")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取汇款单位名单", notes = "获取汇款单位名单")
    @ApiImplicitParam(name = "key", value = "搜索条件")
    public R<IPage<RemitterVo>> getRemitter(PageParamSimple pageParamSimple, String key) {
        PageParam<RemitterVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //体检者结算方式表
        IPage<RemitterVo> iPage = tallyQueryService.getRemitter(page,key);
        return R.ok(iPage);
    }





    /**
     * 支付：体检卡、会员卡、微信支付、支付宝支付等
     *
     * @param param 支付参数
     * @return
     */
    @PostMapping("/pay")
    @ApiOperation(value = "支付", notes = "支付：体检卡、会员卡、微信支付、支付宝支付等", position = 2)
    @Log(title = "记账结算-支付", businessType = BusinessType.INSERT)
    public R<PayResultDto> pay(@RequestBody TallyQueryPayParam param) {
        //TODO wait 实现数据库锁，保证支付信息的一致性
        log.info("记账结算-支付：{}", param);
        PayResultDto dto = tallyQueryService.pay(param);
        return R.ok(dto);
    }




}

