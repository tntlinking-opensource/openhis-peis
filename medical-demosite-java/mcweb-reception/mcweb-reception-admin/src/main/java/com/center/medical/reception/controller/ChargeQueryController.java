package com.center.medical.reception.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Dictpayway;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelExp;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.common.utils.poi.ExcelUtilManySheet;
import com.center.medical.reception.bean.param.*;
import com.center.medical.reception.bean.vo.*;
import com.center.medical.reception.service.ChargeQueryService;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.service.DictpaywayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收费日报(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-02-07 11:31:04
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "收费日报")
@RequestMapping("/reception/chargeQuery")
public class ChargeQueryController extends BaseController {
    /**
     * 服务对象
     */
    private final ChargeQueryService chargeQueryService;
    private final MapperFacade mapperFacade;
    private final DictpaywayService dictpaywayService;
    private final SellcustomerService sellcustomerService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "每日客服报表统计", notes = "每日客服报表统计")
    public R<IPage<CQListVo>> getPage(PageParamSimple pageParamSimple, CQListParam param) {
        PageParam<CQListVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.chargeQueryService.getList(page, param));
    }


    /**
     * 获取收费方式
     *
     * @return
     */
    @GetMapping("/getPaywayData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取收费方式", notes = "获取收费方式")
    public R getPaywayData() {
        List<Dictpayway> list = dictpaywayService.list(new QueryWrapper<Dictpayway>()
                .orderByDesc("createDate"));
        List data = new ArrayList();
        Map<String, Object> result = new HashMap();
        for (Dictpayway branch : list) {
            result = new HashMap();
            result.put("id", branch.getId());
            result.put("fzx", branch.getPaywayName());
            result.put("inputCode", branch.getInputCode());
            data.add(result);
        }
        return R.ok(data);
    }


    /**
     * 导出分页数据
     *
     * @param response
     * @param param
     */
    @Log(title = "导出Excel(客服)", businessType = BusinessType.EXPORT)
    @PostMapping("/everyDayKeFuExport")
    @ApiOperation(value = "导出Excel(客服)", notes = "导出Excel(客服)")
    public void export(HttpServletResponse response, CQListParam param) {
        chargeQueryService.exportData(param);
    }


    /**
     * 今日费用结算情况
     *
     * @return
     */
    @GetMapping("/getBackInfoData")
    @ApiOperation(value = "今日费用结算情况", notes = "今日费用结算情况")
    public R<IPage<BackInfoDataVo>> getBackInfoData(PageParamSimple pageParamSimple, BackInfoDataParam param) {
        PageParam<BackInfoDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<BackInfoDataVo> iPage = chargeQueryService.getBackInfoData(page, param);
        return R.ok(iPage);
    }


    /**
     * 每日记账报表统计
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getEveryDayJZDataList")
    @ApiOperation(value = "每日记账报表统计", notes = "每日记账报表统计")
    public R<IPage<EveryDayJZVo>> getEveryDayJZDataList(PageParamSimple pageParamSimple, EveryDayJZParam param) {
        PageParam<EveryDayJZVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.chargeQueryService.getEveryDayJZDataList(page, param));
    }


    /**
     * 每日自助机通联明细
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getTonglianDataList")
    @ApiOperation(value = "每日自助机通联明细", notes = "每日自助机通联明细")
    public R<IPage<TonglianDataVo>> getTonglianDataList(PageParamSimple pageParamSimple, TongLianDataParam param) {
        PageParam<TonglianDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<TonglianDataVo> iPage = chargeQueryService.getTonglianDataList(page, param);
        return R.ok(iPage);
    }


    /**
     * 导出每日自助通联明细
     *
     * @param response
     * @param param
     */
    @Log(title = "每日客服报表统计", businessType = BusinessType.EXPORT)
    @PostMapping("/exportTongLianData")
    @ApiOperation(value = "导出每日自助通联明细", notes = "导出每日自助通联明细")
    public void exportTongLianData(HttpServletResponse response, TongLianDataParam param) throws IOException {
        List<TonglianDataVo> list = chargeQueryService.exportTongLianData(param);
        //当日所有检查实收费用统计导出数据
        List<SummaryDataVo> list2 = chargeQueryService.getSummaryData(param);
        //导出
        ExcelExp e1 = new ExcelExp("通联支付统计", list, TonglianDataVo.class);
        ExcelExp e2 = new ExcelExp("实收费用统计", list2, SummaryDataVo.class);
        List<ExcelExp> mysheet = new ArrayList<ExcelExp>();
        mysheet.add(e1);
        mysheet.add(e2);
        ExcelUtilManySheet<List<ExcelExp>> util2 = new ExcelUtilManySheet<List<ExcelExp>>(mysheet);
        util2.exportExcelManySheet(response, mysheet);
    }


    /**
     * 当日所有检查统收的统计
     *
     * @param param
     * @return
     */
    @GetMapping("/getEveryDayToTongDataSql")
    @ApiOperation(value = "当日所有检查统收的统计", notes = "当日所有检查统收的统计")
    public R<EDToTongVo> getEveryDayToTongDataSql(EDToTongParam param) {
        EDToTongVo vo = chargeQueryService.getEveryDayToTongDataSql(param);
        return R.ok(vo);
    }


    /**
     * 当日所有检查实收的费用统计
     *
     * @param param
     * @return
     */
    @GetMapping("/getEveryDayToPayWayData")
    @ApiOperation(value = "当日所有检查实收的费用统计", notes = "当日所有检查实收的费用统计")
    public R<ToPayWayVo> getEveryDayToPayWayData(BaseParam param) {
        ToPayWayVo vo = chargeQueryService.getEveryDayToPayWayData(param);
        return R.ok(vo);
    }


    /**
     * 当日团体非统收汇总
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getFeiTongPayData")
    @ApiOperation(value = "当日团体非统收汇总", notes = "当日团体非统收汇总")
    public R<IPage<FeiTongPayVo>> getFeiTongPayData(PageParamSimple pageParamSimple, BaseParam param) {
        PageParam<FeiTongPayVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<FeiTongPayVo> iPage = chargeQueryService.getFeiTongPayData(page, param);
        return R.ok(iPage);
    }


    /**
     * 当日团体非统收汇总总金额
     *
     * @param param
     * @return
     */
    @GetMapping("/getFeiTongPayDataSummarySql")
    @ApiOperation(value = "当日团体非统收汇总总金额", notes = "当日团体非统收汇总总金额")
    public R getFeiTongPayDataSummarySql(BaseParam param) {
        BigDecimal bigDecimal = chargeQueryService.getFeiTongPayDataSummarySql(param);
        return R.ok(bigDecimal);
    }


    /**
     * 获取数据
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getListData")
    @ApiOperation(value = "获取数据", notes = "获取数据")
    public R<IPage<CQListDataVo>> getListData(PageParamSimple pageParamSimple, CQListDataParam param) {
        PageParam<CQListDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<CQListDataVo> iPage = chargeQueryService.getListData(page, param);
        return R.ok(iPage);
    }


    /**
     * 导出(疫苗费用)
     *
     * @param response
     * @param param
     */
    @Log(title = "导出(疫苗费用)", businessType = BusinessType.EXPORT)
    @PostMapping("/exportVaccine")
    @ApiOperation(value = "导出(疫苗费用)", notes = "导出(疫苗费用)")
    public void exportVaccine(HttpServletResponse response, ExportVaccineParam param) {
        List<ExportVaccineVo> list = chargeQueryService.exportVaccine(param);
        ExcelUtil<ExportVaccineVo> util = new ExcelUtil<ExportVaccineVo>(ExportVaccineVo.class);
        util.exportExcel(response, list, "疫苗费用");
    }


    /**
     * 导出(疫苗费用)
     *
     * @param response
     * @param param
     */
    @Log(title = "导出(疫苗名单)", businessType = BusinessType.EXPORT)
    @PostMapping("/exportVaccineName")
    @ApiOperation(value = "导出(疫苗名单)", notes = "导出(疫苗名单)")
    public void exportVaccineName(HttpServletResponse response, ExportVaccineParam param) {
        List<ExportVaccineNameVo> list = chargeQueryService.exportVaccineName(param);
        ExcelUtil<ExportVaccineNameVo> util = new ExcelUtil<ExportVaccineNameVo>(ExportVaccineNameVo.class);
        util.exportExcel(response, list, "疫苗名单统计");
    }


    /**
     * 导出(金蝶名单)
     *
     * @param response
     * @param param
     */
    @Log(title = "导出(金蝶名单)", businessType = BusinessType.EXPORT)
    @PostMapping("/exportKingdeeName")
    @ApiOperation(value = "导出(金蝶名单)", notes = "导出(金蝶名单)")
    public void exportKingdeeName(HttpServletResponse response, ExportVaccineParam param) {
        List<ExportKingdeeNameVo> list = chargeQueryService.exportKingdeeName(param);
        ExcelUtil<ExportKingdeeNameVo> util = new ExcelUtil<ExportKingdeeNameVo>(ExportKingdeeNameVo.class);
        util.exportExcel(response, list, "金蝶名单统计", "金蝶名单统计");
    }


    /**
     * 导出Excel(记账报表)
     *
     * @param response
     * @param param
     */
    @Log(title = "导出Excel(记账报表)", businessType = BusinessType.EXPORT)
    @PostMapping("/exportEveryDay")
    @ApiOperation(value = "导出Excel(记账报表)", notes = "导出Excel(记账报表)")
    public void exportEveryDay(HttpServletResponse response, ExportEveryDayParam param) {
        List<ExportEveryDayVo> list = chargeQueryService.exportEveryDay(param);
        ExcelUtil<ExportEveryDayVo> util = new ExcelUtil<ExportEveryDayVo>(ExportEveryDayVo.class);
        util.exportExcel(response, list, "每日记账报表统计", "每日记账报表统计");
    }





    /**
     * 通联支付总金额
     * @param param
     * @return
     */
    @GetMapping("/getTongLianLimit")
    @ApiOperation(value = "通联支付总金额", notes = "通联支付总金额")
    public R<String> getTongLianLimit(TongLianDataParam param) {
        //获取通联支付总金额
        String limit = chargeQueryService.getTongLianLimit(param);
        return R.ok(limit);
    }




    /**
     * 每日疫苗收费统计
     * @param param
     * @return
     */
    @GetMapping("/getVaccinum")
    @ApiOperation(value = "每日疫苗收费统计", notes = "每日疫苗收费统计")
    public R<IPage<GetVaccinumVo>> getVaccinum(PageParamSimple pageParamSimple,GetVaccinumParam param) {
        PageParam<GetVaccinumVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<GetVaccinumVo> iPage = chargeQueryService.getVaccinum(page, param);
        return R.ok(iPage);
    }



    /**
     * 导出每日疫苗收费统计
     *
     * @param response
     * @param param
     */
    @Log(title = "导出每日疫苗收费统计", businessType = BusinessType.EXPORT)
    @PostMapping("/exportVaccinum")
    @ApiOperation(value = "导出每日疫苗收费统计", notes = "导出每日疫苗收费统计")
    public void exportVaccinum(HttpServletResponse response, GetVaccinumParam param) {
        List<GetVaccinumVo> list = chargeQueryService.exportVaccinum(param);
        ExcelUtil<GetVaccinumVo> util = new ExcelUtil<GetVaccinumVo>(GetVaccinumVo.class);
        util.exportExcel(response, list, "每日疫苗收费统计", "每日疫苗收费统计");
    }
}

