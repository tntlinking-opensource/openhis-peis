package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.PeisReserPayway;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.finance.bean.param.*;
import com.center.medical.finance.bean.param.*;
import com.center.medical.finance.bean.vo.TCAddVo;
import com.center.medical.finance.bean.vo.TCExportVo;
import com.center.medical.finance.bean.vo.TCPageVo;
import com.center.medical.finance.service.TeamChargeService;
import com.center.medical.service.PeisReserPaywayService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 团体结算(Peisorgreservation)表控制层
 *
 * @author ay
 * @since 2023-04-03 16:32:34
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "团体结算")
@RequestMapping("finance/teamCharge")
public class TeamChargeController extends BaseController {
    /**
     * 服务对象
     */
    private final TeamChargeService teamChargeService;
    private final MapperFacade mapperFacade;
    private final PeisReserPaywayService peisReserPaywayService;


    /**
     * 获取【团体结算】这个块业务所有接口及接口说明
     *
     * @return
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【团体结算】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取分中心信息", "GET", "/sell/createorder/getBranchData", "财务管理->体检卡管理-卡充值->获取分中心信息", null),
                new InterfaceVo("获取全部支付方式", "GET", "/dictpayway/getPayWayData", "07.会员系统->客服管理-家庭会员-家庭卡消费->获取全部支付方式", null),
                new InterfaceVo("领取人信息", "GET", "/finance/sendCard/getLqrData", "07.会员系统->客服管理-家庭会员-家庭卡消费->领取人信息", null)
        );
        return R.ok(new FunctionVo("09.财务管理", "团体结算", interfaceVos, "09.财务管理"));
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
    @ApiOperation(value = "分页查询", notes = "分页查询体检者团体任务")
    public R<IPage<TCPageVo>> getPage(PageParamSimple pageParamSimple, TCPageParam param) {
        PageParam<TCPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.teamChargeService.getList(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param param 主键
     * @return 单条数据
     */
    @GetMapping("/add")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "只返回了几个有用的字段,其他字段是上一个页传过来，原封不动再传回去")
    @ApiImplicitParam(name = "param", value = "要查询的对象的主键{id}")
    public R<TCAddVo> selectOne(TCAddParam param) {
        return R.ok(this.teamChargeService.getInfoById(param));
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
    public R saveOrUpdateFee(@RequestBody TeamPayParam param) {
        //TODO wait 实现数据库锁，保证支付信息的一致性
        log.info("记账管理-结算-卡扣款：{}", param);
        return R.toResult(teamChargeService.saveOrUpdateFee(param));
    }

    /**
     * 退款：体检卡、会员卡等
     *
     * @param param 退款参数
     * @return
     */
    @PostMapping("/refund")
//    @PreAuthorize("@ss.hasPermi('reception:registration:charge')")
    @ApiOperation(value = "退款", notes = "退款：体检卡、会员卡等")
    @Log(title = "记账管理-收费管理-退款", businessType = BusinessType.INSERT)
    public R<Boolean> refund(@RequestBody TeamRefundParam param) {
        //TODO wait 实现数据库锁，保证支付信息的一致性
        log.info("记账管理-收费管理-退款：{}", param);
        return R.ok(teamChargeService.refund(param));
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "团体结算保存", notes = "团体结算保存")
    @Log(title = "团体结算保存", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peisorgreservation.id"})
    public R insert(@RequestBody TCSaOrUpParam param) {
        Boolean b = teamChargeService.saOrUp(param, true);
        return R.toResult(b);
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
     * 导出记帐结算明细
     *
     * @param response
     * @param param
     */
    @Log(title = "记帐结算明细", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出团体结算数据", notes = "导出团体结算数据")
    @PostMapping("/export")
    public void export(HttpServletResponse response, TCPageParam param) {
        List<TCExportVo> list = teamChargeService.getExportData(param);
        ExcelUtil<TCExportVo> util = new ExcelUtil<TCExportVo>(TCExportVo.class);
        util.exportExcel(response, list, "记帐结算明细");
    }


}

