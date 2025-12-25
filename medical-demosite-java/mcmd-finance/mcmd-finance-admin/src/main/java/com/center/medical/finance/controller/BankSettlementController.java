package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.finance.bean.model.KdRemittance;
import com.center.medical.finance.bean.model.KdReser;
import com.center.medical.finance.bean.param.BSPageParam;
import com.center.medical.finance.bean.param.BankRefundParam;
import com.center.medical.finance.bean.param.FeeChargerDataParam;
import com.center.medical.finance.bean.param.UpBankRefundParam;
import com.center.medical.finance.bean.vo.*;
import com.center.medical.finance.bean.vo.*;
import com.center.medical.finance.service.BankSettlementService;
import com.center.medical.finance.service.KdPaywayService;
import com.center.medical.system.bean.vo.CreatorDataVo;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 记账管理-银行汇款结算(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-03-31 14:27:31
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "记账管理-银行汇款结算")
@RequestMapping("finance/bankSettlement")
public class BankSettlementController extends BaseController {
    /**
     * 服务对象
     */
    private final BankSettlementService bankSettlementService;
    private final MapperFacade mapperFacade;
    private final KdPaywayService kdPaywayService;
    private final ISysUserService sysUserService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
    public R<IPage<BSPageVo>> getPage(PageParamSimple pageParamSimple, BSPageParam param) {
        PageParam<BSPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.bankSettlementService.getList(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param transactionNumber 交易流水号
     * @return 单条数据
     */
    @GetMapping("/bankRefund/{transactionNumber}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据流水号查询详情")
    @ApiImplicitParam(name = "transactionNumber", value = "交易流水号")
    public R<KdRemittance> selectOne(@PathVariable String transactionNumber) {
        return R.ok(this.bankSettlementService.getInfoByNum(transactionNumber));
    }


    /**
     * 导出统收
     *
     * @param response
     * @param param
     */
    @Log(title = "导出统收", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出统收", notes = "导出统收")
    @PostMapping("/export")
    public void export(HttpServletResponse response, BankRefundParam param) {
        List<BankRefundVo> list = bankSettlementService.exportBankRefund(param);
        ExcelUtil<BankRefundVo> util = new ExcelUtil<BankRefundVo>(BankRefundVo.class);
        util.exportExcel(response, list, "银行流水统计");
    }


    /**
     * 获取一笔银行流水相关的详细记账结算
     *
     * @param pageParamSimple
     * @param transactionNumber
     * @return
     */
    @GetMapping("/getReserBillingData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取一笔银行流水相关的详细记账结算", notes = "获取一笔银行流水相关的详细记账结算")
    @ApiImplicitParam(name = "transactionNumber", value = "交易流水号")
    public R<IPage<KdReser>> getReserBillingData(PageParamSimple pageParamSimple, String transactionNumber) {
        PageParam<KdReser> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<KdReser> iPage = bankSettlementService.getReserBillingData(page, transactionNumber);
        return R.ok(iPage);
    }


    /**
     * 获取银行结算方式列表
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getKingdeeReserWay")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取银行结算方式列表", notes = "获取银行结算方式列表")
    @ApiImplicitParam(name = "key", value = "搜索条件")
    public R<IPage<KingdeeReserWayVo>> getKingdeeReserWay(PageParamSimple pageParamSimple, String key) {
        PageParam<KingdeeReserWayVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<KingdeeReserWayVo> iPage = kdPaywayService.getKingdeeReserWay(page, key);
        return R.ok(iPage);
    }


    /**
     * 获取创建人
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getCreatorData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取创建人", notes = "获取创建人")
    @ApiImplicitParam(name = "key", value = "模糊查询姓名")
    public R<IPage<CreatorDataVo>> getCreatorData(PageParamSimple pageParamSimple, String key) {
        PageParam<CreatorDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<CreatorDataVo> iPage = sysUserService.getCreatorData(page, key);
        return R.ok(iPage);
    }


    /**
     * 获取结算名字
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getNameNumber")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取结算名字", notes = "获取结算名字")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "搜索条件"),
            @ApiImplicitParam(name = "way", value = "付款方式")
    })
    public R<IPage<NameNumberVo>> getNameNumber(PageParamSimple pageParamSimple, String way, String key) {
        PageParam<NameNumberVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<NameNumberVo> iPage = bankSettlementService.getNameNumber(page,way,key);
        return R.ok(iPage);
    }


    /**
     * 获取结算名字（id号那列）
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getIdCustomer")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取结算名字（id号那列）", notes = "获取结算名字（id号那列）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "搜索条件"),
            @ApiImplicitParam(name = "way", value = "付款方式")
    })
    public R<IPage<NameNumberVo>> getIdCustomer(PageParamSimple pageParamSimple,String way,String key) {
        PageParam<NameNumberVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<NameNumberVo> iPage = bankSettlementService.getIdCustomer(page, key,way);
        return R.ok(iPage);
    }


    /**
     * 银行汇款结算-保存
     *
     * @param param
     * @return
     */
    @PostMapping("/updateBankRefund")
    @ApiOperation(value = "银行汇款结算-保存", notes = "银行汇款结算-保存")
    public R updateBankRefund(@RequestBody UpBankRefundParam param) {
        Boolean b = bankSettlementService.updateBankRefund(param);
        return R.toResult(b);
    }


    /**
     * 判断当前登录者是否为财务
     *
     * @return
     */
    @GetMapping("/isCaiWu")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "判断当前登录者是否为财务", notes = "判断当前登录者是否为财务")
    public R isCaiWu() {
        //获取当前登录用户是否为财务
        Boolean b = SecurityUtils.hasRole(RoleAuthName.CAIWU);
        if (b) {
            return R.ok("success");
        } else {
            return R.ok("error");
        }
    }


    /**
     * 银行汇款结算-保存
     *
     * @param rowsId
     * @return
     */
    @PutMapping("/approve")
    @ApiOperation(value = "审核", notes = "审核")
    @ApiImplicitParam(name = "rowsId", value = "id的集合")
    public R approve(@RequestParam List<String> rowsId) {
        String s = bankSettlementService.approve(rowsId);
        return R.ok(s);
    }


    /**
     * 反审核
     *
     * @param rowsId
     * @return
     */
    @PutMapping("/unapprove")
    @ApiOperation(value = "反审核", notes = "反审核")
    @ApiImplicitParam(name = "rowsId", value = "id的集合")
    public R unapprove(@RequestParam List<String> rowsId) {
        String s = bankSettlementService.unapprove(rowsId);
        return R.ok(s);
    }


    /**
     * 获取销售经理
     * @param param
     * @return
     */
    @GetMapping("/getFeeChargerData")
    @ApiOperation(value = "获取销售经理", notes = "获取销售经理")
    public R<IPage<FeeChargerDataVo>> getFeeChargerData(PageParamSimple pageParamSimple, FeeChargerDataParam param) {
        PageParam<FeeChargerDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<FeeChargerDataVo> iPage = bankSettlementService.getFeeChargerData(page,param);
        return R.ok(iPage);
    }


    /**
     * 查询汇总金额
     * @param param
     * @return
     */
    @GetMapping("/summaryAmount")
    @ApiOperation(value = "查询汇总金额", notes = "查询汇总金额")
    public R<BankAmountVo> summaryAmount(BSPageParam param) {
        BankAmountVo vo = bankSettlementService.summaryAmount(param);
        return R.ok(vo);
    }
}


