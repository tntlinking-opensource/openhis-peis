package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.ReceiptType;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.finance.bean.param.IRPageParam;
import com.center.medical.finance.bean.param.IRSaOrUpParam;
import com.center.medical.finance.bean.vo.IRPageVo;
import com.center.medical.finance.service.InvoiceRequestService;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.Peisorgreservationreceipt;
import com.center.medical.sellcrm.service.CreateorderService;
import com.center.medical.service.ReceiptTypeService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
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
 * 发票管理(Peisorgreservationreceipt)表控制层
 *
 * @author ay
 * @since 2023-04-04 11:00:04
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "发票管理")
@RequestMapping("finance/invoiceRequest")
public class InvoiceRequestController extends BaseController {
    /**
     * 服务对象
     */
    private final InvoiceRequestService invoiceRequestService;
    private final MapperFacade mapperFacade;
    private final ReceiptTypeService receiptTypeService;
    private final CreateorderService createorderService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询发票")
    public R<IPage<IRPageVo>> getPage(PageParamSimple pageParamSimple, IRPageParam param) {
        PageParam<IRPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.invoiceRequestService.getList(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查发票详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Peisorgreservationreceipt> selectOne(@PathVariable String id) {
        return R.ok(this.invoiceRequestService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "发票申请保存", notes = "发票申请保存")
    @Log(title = "发票", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peisorgreservationreceipt.id"})
    public R insert(@RequestBody IRSaOrUpParam param) {
        Boolean b = invoiceRequestService.saOrUp(param);
        return R.toResult(b);
    }


    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除发票")
    @Log(title = "发票", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.invoiceRequestService.removeIds(ids));
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "发票导出", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "发票导出", notes = "发票导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, IRPageParam param) {
        List<IRPageVo> list = invoiceRequestService.getExportData(param);
        ExcelUtil<IRPageVo> util = new ExcelUtil<IRPageVo>(IRPageVo.class);
        util.exportExcel(response, list, "发票导出");
    }


    /**
     * 获取图表数据
     *
     * @param param
     * @return
     */
    @GetMapping("/getBarData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取图表数据", notes = "获取图表数据")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<List<String>> getBarData(BaseParam param) {
        return R.ok(this.invoiceRequestService.getBarData(param));
    }


    /**
     * 获取发票类型
     *
     * @return
     */
    @GetMapping("/getTypeData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取发票类型", notes = "获取发票类型")
    public R<List<ReceiptType>> getTypeData() {
        //发票类型
        List<ReceiptType> list = receiptTypeService.list(new QueryWrapper<ReceiptType>().orderByDesc("createdate"));
        return R.ok(list);
    }


    /**
     * 获取订单号下拉
     *
     * @param ddh 主键
     * @return 单条数据
     */
    @GetMapping("/getDdhData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取订单号下拉", notes = "获取订单号下拉")
    @ApiImplicitParam(name = "ddh", value = "订单号")
    public R getDdhData(PageParamSimple pageParamSimple, String ddh) {
        PageParam<Createorder> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<Createorder> iPage = createorderService.getDdhData(page, ddh, null);
        return R.ok(iPage);
    }


    /**
     * 复制
     *
     * @return
     */
    @GetMapping("/copy")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "复制", notes = "复制方法没写,跟详情一样的。原理其实就是调用的详情接口，然后赋值过去")
    public R copy() {
        return R.ok();
    }


    /**
     * 审核
     *
     * @param id
     * @return
     */
    @PutMapping("/examine")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "审核", notes = "审核")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R examine(@RequestParam String id) {
        Boolean b = invoiceRequestService.examine(id);
        return R.toResult(b);
    }


    /**
     * 反审核
     *
     * @param id
     * @param unauditNote
     * @return
     */
    @PutMapping("/unauditSave")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "反审核", notes = "反审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}"),
            @ApiImplicitParam(name = "unauditNote", value = "反审核备注")
    })
    public R unauditSave(@RequestParam String id, @RequestParam String unauditNote) {
        Boolean b = invoiceRequestService.unauditSave(id, unauditNote);
        return R.toResult(b);
    }


    /**
     * 审核不通过
     *
     * @param id
     * @return
     */
    @PutMapping("/unapprove")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "审核不通过", notes = "审核不通过")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R unapprove(@RequestParam String id) {
        Boolean b = invoiceRequestService.unapprove(id);
        return R.toResult(b);
    }


    /**
     * 出票信息保存
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdatePrint")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "出票信息保存", notes = "出票信息保存")
    @Log(title = "发票", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peisorgreservationreceipt.id"})
    public R saveOrUpdatePrint(@RequestBody IRSaOrUpParam param) {
        Boolean b = invoiceRequestService.saOrUpPrint(param);
        return R.toResult(b);
    }


    /**
     * 换票申请保存
     *
     * @param param
     * @return
     */
    @PostMapping("/saveReturnApply")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "换票申请保存", notes = "换票申请保存")
    @Log(title = "发票", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peisorgreservationreceipt.id"})
    public R saveReturnApply(@RequestBody IRSaOrUpParam param) {
        Boolean b = invoiceRequestService.saveReturnApply(param);
        return R.toResult(b);
    }


    /**
     * 换票撤回
     *
     * @param id
     * @return
     */
    @PutMapping("/saveReturnCancle")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "换票撤回", notes = "换票撤回")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R saveReturnCancle(@RequestParam String id) {
        Boolean b = invoiceRequestService.saveReturnCancle(id);
        return R.toResult(b);
    }


    /**
     * 换票审核
     *
     * @param param
     * @return
     */
    @PostMapping("/saveReturnAudit")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "换票审核", notes = "换票审核")
    @Log(title = "发票", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peisorgreservationreceipt.id"})
    public R saveReturnAudit(@RequestBody IRSaOrUpParam param) {
        Boolean b = invoiceRequestService.saveReturnAudit(param);
        return R.toResult(b);
    }


    /**
     * 换票反审核
     *
     * @param param
     * @return
     */
    @PostMapping("/saveReturnUnaudit")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "换票反审核", notes = "换票反审核")
    @Log(title = "发票", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peisorgreservationreceipt.id"})
    public R saveReturnUnaudit(@RequestBody IRSaOrUpParam param) {
        Boolean b = invoiceRequestService.saveReturnUnaudit(param);
        return R.toResult(b);
    }


    /**
     * 换票
     *
     * @param param
     * @return
     */
    @PostMapping("/saveReturnConfirm")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "换票", notes = "换票")
    @Log(title = "发票", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peisorgreservationreceipt.id"})
    public R saveReturnConfirm(@RequestBody IRSaOrUpParam param) {
        Boolean b = invoiceRequestService.saveReturnConfirm(param);
        return R.toResult(b);
    }

}

