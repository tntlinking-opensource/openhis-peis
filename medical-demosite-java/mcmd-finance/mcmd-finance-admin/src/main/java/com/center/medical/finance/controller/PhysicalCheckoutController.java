package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.finance.bean.dto.AccountsTotalDto;
import com.center.medical.finance.bean.param.AccountsInfoParam;
import com.center.medical.finance.bean.param.AnalyseParam;
import com.center.medical.finance.bean.vo.AccountsInfoVo;
import com.center.medical.finance.bean.vo.AnalyseVo;
import com.center.medical.finance.bean.vo.ExportItemsVo;
import com.center.medical.finance.service.PhysicalCheckoutService;
import com.center.medical.reception.service.OrderService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.service.ISysBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 体检结账单(Createorder)表控制层
 *
 * @author ay
 * @since 2023-03-11 18:01:03
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检结账单")
@RequestMapping("finance/physicalCheckout")
public class PhysicalCheckoutController extends BaseController {
    /**
     * 服务对象
     */
    private final PhysicalCheckoutService physicalCheckoutService;
    private final MapperFacade mapperFacade;
    private final ISysBranchService sysBranchService;
    private final OrderService orderService;
    private final PeispatientService peispatientService;


    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【财务管理-体检结账单】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取全部支付方式", "GET", "/dictpayway/getPayWayData", "07.会员系统->客服管理-家庭会员-家庭卡消费->获取全部支付方式", null)

        );
        return R.ok(new FunctionVo("14.财务管理", "体检结账单", interfaceVos, "14.财务管理"));
    }


    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/analyse")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "左上-体检结账单数据", notes = "体检结账单数据")
    public R<IPage<AnalyseVo>> getPage(PageParamSimple pageParamSimple, AnalyseParam param) {
        PageParam<AnalyseVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.physicalCheckoutService.getList(page, param));
    }


    /**
     * 获取所有分中心
     *
     * @param
     * @return
     */
    @GetMapping("/fzx")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取所有分中心", notes = "获取所有分中心")
    public R<List<SysBranch>> fzx() {
        List<SysBranch> list = sysBranchService.list(new QueryWrapper<SysBranch>()
                .eq("is_delete", 0));
        return R.ok(list);
    }


    /**
     * 查看右上角分组
     *
     * @param idOrgRw
     * @return
     */
    @GetMapping("/getGroupData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "右上-分组套餐", notes = "查看右上角分组套餐")
    @ApiImplicitParam(name = "idOrgRw", value = "体检团体任务ID")
    public R<Map> getGroupData(String idOrgRw) {
        Map map = orderService.getGroupData(idOrgRw,null);
        return R.ok(map);
    }


    /**
     * 查看左中体检人数据
     *
     * @param param
     * @return
     */
    @GetMapping("/getAccountsInfoData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "左中-体检人数据", notes = "查看左中体检人数据")
    public R<IPage<AccountsInfoVo>> getAccountsInfoData(PageParamSimple pageParamSimple, AccountsInfoParam param) {
        PageParam<AccountsInfoVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<AccountsInfoVo> iPage = physicalCheckoutService.getAccountsInfoData(page, param);
        return R.ok(iPage);
    }


    /**
     * 右上-禁检或反禁检
     *
     * @param type
     * @param ids
     * @return
     */
    @PutMapping("/updateGroupLimit")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "右上-禁检或反禁检", notes = "右上-禁检或反禁检")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "禁检1,反禁检0"),
            @ApiImplicitParam(name = "ids", value = "id集合")
    })
    @Log(title = "右上-禁检或反禁检", businessType = BusinessType.INSERT)
    public R updateGroupLimit(@RequestParam Integer type, @RequestParam List<String> ids) {
        Boolean b = physicalCheckoutService.updateGroupLimit(type, ids);
        return R.toResult(b);
    }


    /**
     * 左中-禁检或反禁检
     *
     * @param paused
     * @param ids
     * @return
     */
    @PutMapping("/savePausedStatus")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "左中-禁检或反禁检", notes = "右上-禁检或反禁检")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paused", value = "禁检1,反禁检0"),
            @ApiImplicitParam(name = "ids", value = "id集合")
    })
    @Log(title = "左中-禁检或反禁检", businessType = BusinessType.INSERT)
    public R savePausedStatus(@RequestParam Integer paused, @RequestParam List<String> ids) {
        Boolean b = physicalCheckoutService.savePausedStatus(paused, ids);
        return R.toResult(b);
    }


    /**
     * 左中-已结账
     *
     * @param ids
     * @return
     */
    @PutMapping("/finishStatus")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "左中-已结账", notes = "左中-已结账")
    @ApiImplicitParam(name = "ids", value = "id集合")
    public R finishStatus(@RequestParam List<String> ids) {
        Boolean b = physicalCheckoutService.finishStatus(ids);
        return R.toResult(b);
    }


    /**
     * 左中-反结账
     *
     * @param ids
     * @return
     */
    @PutMapping("/unfinishStatus")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "左中-反结账", notes = "左中-反结账")
    @ApiImplicitParam(name = "ids", value = "id集合")
    public R unfinishStatus(@RequestParam List<String> ids) {
        Boolean b = physicalCheckoutService.unfinishStatus(ids);
        return R.toResult(b);
    }


    /**
     * 左中-未检禁检
     *
     * @param paused
     * @param ids
     * @return
     */
    @PutMapping("/unSavePausedStatus")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "左中-未检禁检", notes = "左中-未检禁检")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paused", value = "禁检1,反禁检0"),
            @ApiImplicitParam(name = "ids", value = "id集合")
    })
    @Log(title = "左中-未检禁检", businessType = BusinessType.INSERT)
    public R unSavePausedStatus(@RequestParam Integer paused, @RequestParam List<String> ids) {
        Boolean b = physicalCheckoutService.unSavePausedStatus(paused, ids);
        return R.toResult(b);
    }


    /**
     * 右中-项目列表数据
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/getItemListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "右中-项目列表数据", notes = "右中-项目列表数据")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R getItemListData(String patientcode) {
        List<Map<String, Object>> list = physicalCheckoutService.getItemListData(patientcode);
        return R.ok(list);
    }


    /**
     * 右下-收费信息
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/getChargeData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "右下-收费信息", notes = "右下-收费信息")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R getChargeData(String patientcode) {
        List<Map<String, Object>> list = physicalCheckoutService.getChargeData(patientcode);
        return R.ok(list);
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "体检人员", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出体检人员", notes = "导出体检人员")
    @PostMapping("/exportAccountsInfoData")
    public void exportAccountsInfoData(HttpServletResponse response, AccountsInfoParam param) throws IOException {
        physicalCheckoutService.exportAccountsInfoData(param);
    }


    /**
     * 导出收费项目
     *
     * @param response
     * @param patientCode
     */
    @Log(title = "体检人员", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出收费项目", notes = "导出收费项目")
    @PostMapping("/exportItems")
    public void exportItems(HttpServletResponse response, String patientCode) {
		List<ExportItemsVo> list = physicalCheckoutService.exportItems(patientCode);
        Peispatient peispatient = peispatientService.getByPatientCode(patientCode);
        ExcelUtil<ExportItemsVo> util = new ExcelUtil<ExportItemsVo>(ExportItemsVo.class);
        util.exportExcel(response, list, "收费项目列表-顾客姓名"+peispatient.getPatientname()+" 体检号："+patientCode);
    }



    /**
     * 右下-收费信息
     *
     * @param ddh
     * @return
     */
    @GetMapping("/synchronizedChecked")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "同步已检", notes = "把老系统登记的体检号，在新系统变成禁检")
    @ApiImplicitParam(name = "ddh", value = "订单号")
    public R synchronizedChecked(String ddh) {
        Boolean b = physicalCheckoutService.synchronizedChecked(ddh);
        return R.ok(b);
    }





    /**
     * 获取合计数据
     *
     * @param param
     */
    @ApiOperation(value = "获取合计数据", notes = "获取合计数据")
    @GetMapping("/getAccountsTotalDto")
    public R getAccountsTotalDto(AccountsInfoParam param) {
        List<AccountsTotalDto> list = physicalCheckoutService.exportAccountsTotalDto(param);
        return R.ok(list);
    }
}

