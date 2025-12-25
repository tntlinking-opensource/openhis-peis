package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.enums.MarriageType;
import com.center.medical.bean.model.Branch;
import com.center.medical.bean.model.Nation;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.reception.service.OrderService;
import com.center.medical.sellcrm.bean.param.*;
import com.center.medical.sellcrm.bean.vo.AllTcOrderVo;
import com.center.medical.sellcrm.bean.vo.ItemOnlineVo;
import com.center.medical.sellcrm.bean.vo.PatientDataVo;
import com.center.medical.sellcrm.service.ItemListOnlineService;
import com.center.medical.service.NationService;
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
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 订单表(Createorder)表控制层
 *
 * @author ay
 * @since 2023-03-11 18:01:03
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "线上备单")
@RequestMapping("sell/itemListOnline")
public class ItemListOnlineController extends BaseController {
    /**
     * 服务对象
     */
    private final ItemListOnlineService itemListOnlineService;
    private final MapperFacade mapperFacade;
    private final NationService nationService;
    private final OrderService orderService;

    /**
     * 【线上备单】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【线上备单】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取客户单位下拉", "GET", "/sell/customer/getAllOrg", "06.客户销售系统->销售管理-线上备单->获取客户单位下拉", null),
                new InterfaceVo("收款方式下拉", "GET", "/dictpayway/getPayWayData", "06.客户销售系统->销售管理-线上备单->收款方式下拉", null)
        );
        return R.ok(new FunctionVo("06.客户销售系统", "销售管理-线上备单", interfaceVos, "06.客户销售系统"));
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
    @ApiOperation(value = "分页查询", notes = "分页查询订单表")
    public R<IPage<ItemOnlineVo>> getPage(PageParamSimple pageParamSimple, ItemOnlineParam param) {
        PageParam<ItemOnlineVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.itemListOnlineService.getList(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/edit")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查订单表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Map> selectOne(String id) {
        Map map = itemListOnlineService.getInfoById(id);
        Integer ishaiguan = Constants.ISHAIGUAN;
        map.put("isHaiguan", ishaiguan == 1 ? 1 : 0);
        return R.ok(map);
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdateGroup")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "体检团体分组保存", notes = "体检团体分组保存")
    @Log(title = "订单表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"createorder.id"})
    public R saveOrUpdateGroup(@RequestBody SaOrUpGroupParam param) {
        return R.ok(this.itemListOnlineService.saveOrUpdateGroup(param));
    }


    /**
     * 导出Excel
     *
     * @param response
     * @param param
     */
    @Log(title = "线上备单", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出Excel", notes = "导出Excel")
    @PostMapping("/export")
    public void export(HttpServletResponse response, DbOrderParam param) throws IOException {
        //导出多sheet
        orderService.getExportData(response,param);
    }


    /**
     * 获取体检团体分组信息
     *
     * @param idOrgRw
     * @return
     */
    @GetMapping("/getGroupData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取体检团体分组信息", notes = "获取体检团体分组信息")
    @ApiImplicitParam(name = "idOrgRw", value = "体检团体任务ID")
    public R<Map> getGroupData(String idOrgRw) {
        Map map = itemListOnlineService.getGroupData(idOrgRw);
        return R.ok(map);
    }


    /**
     * 获取订单下的所有套餐
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getAllTcForOrder")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取订单下的所有套餐", notes = "获取订单下的所有套餐")
    public R<IPage<AllTcOrderVo>> getAllTcForOrder(PageParamSimple pageParamSimple, AllTcOrderParam param) {
        PageParam<AllTcOrderVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<AllTcOrderVo> iPage = itemListOnlineService.getAllTcForOrder(page, param);
        return R.ok(iPage);
    }


    /**
     * 获取套餐的分中心
     *
     * @param id
     * @return
     */
    @GetMapping("/getBranchData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取套餐的分中心", notes = "获取套餐的分中心")
    @ApiImplicitParam(name = "id", value = "套餐id")
    public R<List<Branch>> getBranchData(String id) {
        List<Branch> list = itemListOnlineService.getBranchData(id);
        return R.ok(list);
    }


    /**
     * 备单确认
     *
     * @param ids
     * @return
     */
    @PutMapping("/groupConfirm")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "备单确认", notes = "备单确认")
    @ApiImplicitParam(name = "ids", value = "分组id集合")
    public R groupConfirm(@RequestParam List<String> ids) {
        Boolean b = itemListOnlineService.groupConfirm(ids);
        return R.toResult(b);
    }


    /**
     * 获取分组下相应的人员信息
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getPatientData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取分组下相应的人员信息", notes = "获取分组下相应的人员信息")
    public R<IPage<PatientDataVo>> getPatientData(PageParamSimple pageParamSimple, PatientDataParam param) {
        PageParam<PatientDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(itemListOnlineService.getPatientData(page, param));
    }


    /**
     * 婚姻下拉
     *
     * @return
     */
    @GetMapping("/getMarriageData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "婚姻下拉", notes = "婚姻下拉")
    public R getMarriageData() {
        return R.ok(MarriageType.toMap());
    }


    /**
     * 获取民族数据
     *
     * @return
     */
    @GetMapping("/getNationData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取民族数据", notes = "获取民族数据")
    public R getNationData() {
        List<Nation> nationList = nationService.list();
        return R.ok(nationList);
    }


    /**
     * 体检者基本信息保存（预登记）
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdatePatient")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "预登记", notes = "体检者基本信息保存（预登记）")
    @Log(title = "订单表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"createorder.id"})
    public R saveOrUpdatePatient(@RequestBody SaOrUpPatientParam param) {
        Boolean b = itemListOnlineService.saveOrUpdatePatient(param);
        return R.toResult(b);
    }


    /**
     * 保存
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdatePatientBc")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "保存", notes = "保存")
    @Log(title = "订单表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"createorder.id"})
    public R saveOrUpdatePatientBc(@RequestBody SaOrUpPatientParam param) {
        Boolean b = itemListOnlineService.saveOrUpdatePatient(param);
        return R.toResult(b);
    }


    /**
     * 清除
     *
     * @param id
     * @param ids
     * @return
     */
    @DeleteMapping("/removeAll")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "清除", notes = "清除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "任务分组ID"),
            @ApiImplicitParam(name = "ids", value = "删除的体检者id")
    })
    public R removeAll(@RequestParam String id, @RequestParam List<String> ids) {
        Boolean b = itemListOnlineService.removeAll(id, ids);
        return R.toResult(b);
    }


    /**
     * 已备单
     *
     * @param ids
     * @return
     */
    @PostMapping("/markTbzt")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "已备单", notes = "已备单")
    @ApiImplicitParam(name = "ids", value = "订单id集合")
    public R markTbzt(@RequestParam List<String> ids) {
        Boolean b = itemListOnlineService.markTbzt(ids);
        return R.toResult(b);
    }


    /**
     * 订单结束/反结束
     *
     * @param ids
     * @param type
     * @return
     */
    @PutMapping("/finishOrder")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "订单结束/反结束", notes = "订单结束/反结束")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "订单id集合"),
            @ApiImplicitParam(name = "type", value = "订单结束1,反结束0")
    })
    @Log(title = "订单结束/反结束", businessType = BusinessType.INSERT)
    public R finishOrder(@RequestParam List<String> ids, @RequestParam int type) {
        Boolean b = itemListOnlineService.finishOrder(ids, type);
        return R.toResult(b);
    }

}

