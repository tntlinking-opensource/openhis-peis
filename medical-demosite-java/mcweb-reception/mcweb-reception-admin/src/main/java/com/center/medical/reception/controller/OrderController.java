package com.center.medical.reception.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.model.BaseWorktype;
import com.center.medical.data.service.BaseWorktypeService;
import com.center.medical.reception.bean.dto.CheckListDto;
import com.center.medical.reception.bean.param.*;
import com.center.medical.reception.bean.vo.BdOrderVo;
import com.center.medical.reception.bean.vo.OrderPaDataVo;
import com.center.medical.reception.bean.vo.StatisticsVo;
import com.center.medical.reception.service.OrderService;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.param.AllTcOrderParam;
import com.center.medical.sellcrm.bean.param.DbOrderParam;
import com.center.medical.sellcrm.bean.param.SaOrUpGroupParam;
import com.center.medical.sellcrm.bean.param.SaOrUpPatientParam;
import com.center.medical.sellcrm.bean.vo.AllTcOrderVo;
import com.center.medical.sellcrm.service.ItemListOnlineService;
import com.center.medical.sellcrm.service.MealandfzxService;
import com.center.medical.sellcrm.service.OrderandfzxService;
import com.center.medical.system.bean.param.AddSysUserBranchParam;
import com.center.medical.system.bean.vo.AllUserDataVo;
import com.center.medical.system.service.ISysUserService;
import com.center.medical.system.service.SysUserBranchService;
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
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 前台-备单控制层
 *
 * @author 路飞船长
 * @since 2022-12-02 11:51:13
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "前台管理-备单")
@RequestMapping("/reception/order")
public class OrderController extends BaseController {
    /**
     * 服务对象
     */
    private final OrderService orderService;
    private final MapperFacade mapperFacade;
    private final ItemListOnlineService itemListOnlineService;
    private final BaseWorktypeService baseWorktypeService;
    private final ISysUserService sysUserService;
    private final OrderandfzxService orderandfzxService;
    private final MealandfzxService mealandfzxService;
    private final SysUserBranchService sysUserBranchService;


    /**
     * 【前台-备单】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【前台管理-备单】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("获取客户单位下拉", "GET", "/sell/customer/getAllOrg", "06.客户销售系统->销售管理-线上备单->获取客户单位下拉", null),
                new InterfaceVo("收款方式下拉", "GET", "/dictpayway/getPayWayData", "06.客户销售系统->销售管理-线上备单->收款方式下拉", null),
                new InterfaceVo("来检短信提醒-姓名下拉", "GET", "/finance/sendCard/getLqrData", "06.客户销售系统->销售管理-线上备单->收款方式下拉", null)
        );
        return R.ok(new FunctionVo("08.前台-预约系统", "前台管理-备单", interfaceVos, "08.前台-预约系统"));
    }


    /**
     * 分页查询备单订单列表
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取订单列表", notes = "分页查询获取订单列表")
    public R<IPage<BdOrderVo>> getPage(PageParamSimple pageParamSimple, DbOrderParam param) {
        PageParam<Createorder> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.orderService.getPage(page, param));
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
     * 查看套餐
     *
     * @param idOrgRw
     * @return
     */
    @GetMapping("/getGroupData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查看套餐", notes = "查看套餐")
    @ApiImplicitParam(name = "idOrgRw", value = "体检团体任务ID")
    public R<Map> getGroupData(String idOrgRw,String tjtcmc) {
        if (StringUtils.isNotEmpty(tjtcmc)){
            tjtcmc = tjtcmc.trim();
        }
        Map map = orderService.getGroupData(idOrgRw,tjtcmc);
        return R.ok(map);
    }


    /**
     * 状态重置
     *
     * @param orderIds
     * @param cid
     * @return
     */
    @PostMapping("/returnToZero")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "状态重置", notes = "重置所选订单及其套餐在网上的下载状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderIds", value = "订单id"),
            @ApiImplicitParam(name = "cid", value = "分中心id")
    })
    public R returnToZero(@RequestParam List<String> orderIds,@RequestParam String cid) {
       Boolean b = orderService.returnToZero(orderIds,cid);
        return R.toResult(b);
    }


    /**
     * 备单管理-上方数据
     *
     * @param id
     * @return
     */
    @GetMapping("/edit")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "备单管理-上方数据", notes = "备单管理-上方数据")
    @ApiImplicitParam(name = "id", value = "id")
    public R<Map> edit(String id) {
        Map map = orderService.edit(id);
        return R.ok(map);
    }


    /**
     * 工种信息查询
     *
     * @param typeName
     * @return
     */
    @GetMapping("/getBaseWorktypeSql")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "工种信息查询", notes = "工种信息查询")
    @ApiImplicitParam(name = "typeName", value = "工种名称")
    public R<IPage<BaseWorktype>> getBaseWorktypeSql(PageParamSimple pageParamSimple, String typeName) {
        PageParam<BaseWorktype> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<BaseWorktype> list = baseWorktypeService.getBaseWorktypeSql(page, typeName);
        return R.ok(list);
    }


    /**
     * 设置工种
     *
     * @param id
     * @param ids
     * @return
     */
    @PutMapping("/setWorktype")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "设置工种", notes = "设置工种")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "工种id"),
            @ApiImplicitParam(name = "ids", value = "分组id多个")
    })
    public R setWorktype(@RequestParam String id, @RequestParam List<String> ids) {
        Boolean b = orderService.setWorktype(id, ids);
        return R.toResult(b);
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
     * 体检团体分组保存
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdateGroup")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "体检团体分组保存", notes = "体检团体分组保存")
    @Log(title = "订单表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"createorder.id"})
    public R saveOrUpdateGroup(@RequestBody SaOrUpGroupParam param) {
        return R.ok(orderService.saveOrUpdateGroup(param));
    }


    /**
     * 备单状态更改
     *
     * @param orderId
     * @return
     */
    @PutMapping("/updateNotifyRemoteOrder")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "备单状态更改(通知销售)", notes = "备单状态更改(通知销售)")
    @ApiImplicitParam(name = "orderId", value = "订单id")
    public R updateNotifyRemoteOrder(@RequestParam String orderId) {
        Map<String, Object> info = new HashMap<String, Object>();
        String fzxId = SecurityUtils.getCId();
        try {
            String result = orderService.updateNotifyRemoteOrder(orderId, fzxId);
            if (result.equals("success")) {
                info.put("success", "1");
                info.put("info", "通知成功");
            } else {
                info.put("success", "1");
                info.put("info", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            info.put("success", "-1");
            info.put("info", e.getMessage());
        }
        return R.ok(info);
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
    public R<IPage<OrderPaDataVo>> getPatientData(PageParamSimple pageParamSimple, OrderPaDataParam param) {
        PageParam<OrderPaDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<OrderPaDataVo> iPage = orderService.getPatientData(page, param);
        return R.ok(iPage);
    }


    /**
     * 体检者基本信息保存（预登记）
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdatePatient")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @RepeatSubmit(interval = 3000, message = "保存中，请稍等")
    @ApiOperation(value = "预登记", notes = "体检者基本信息保存（预登记）")
    @Log(title = "订单表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"createorder.id"})
    public R saveOrUpdatePatient(@RequestBody SaOrUpPatientParam param) {
        Boolean b = orderService.saveOrUpdatePatient(param);
        return R.toResult(b);
    }


    /**
     * 来检短信提醒-保存
     *
     * @param param
     * @return
     */
    @PostMapping("/saveSmsToExam")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "来检短信提醒-保存", notes = "来检短信提醒-保存")
    public R saveSmsToExam(@RequestBody SmsToExamParam param) {
        Boolean b = orderService.saveSmsToExam(param);
        return R.toResult(b);
    }


    /**
     * 批量设置
     *
     * @param data
     * @param id
     * @return
     */
    @PutMapping("/updatehy")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "批量设置", notes = "批量设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "体检号集合"),
            @ApiImplicitParam(name = "id", value = "普通会员1，vip会员2，vvip会员3")
    })
    public R updatehy(@RequestParam List<String> data, @RequestParam String id) {
        Boolean b = orderService.updatehy(data, id);
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
    @RepeatSubmit(interval = 3000, message = "保存中，请稍等")
    @Log(title = "订单表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"createorder.id"})
    public R saveOrUpdatePatientBc(@RequestBody SaOrUpPatientParam param) {
        Boolean b = orderService.saveOrUpdatePatient(param);
        return R.toResult(b);
    }


    /**
     * 导入名单
     *
     * @param param
     * @return
     */
    @PostMapping("/importPatientBatch")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "导入名单", notes = "导入名单")
    @Log(title = "备单管理-导入名单", businessType = BusinessType.IMPORT)
    public R importPatientBatch(ImportPatientBatchParam param) {

        if (Objects.isNull(param.getFile())) {
            throw new GlobalException("请选择上传文件！");
        }
        String fileName = param.getFile().getOriginalFilename();
        //判断文件后缀
        String endSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        if (!endSuffix.toLowerCase().endsWith("xls") && !endSuffix.toLowerCase().endsWith("xlsx")) {
            throw new GlobalException("请选择正确的文件类型，必须是以.xls或.xlsx结尾！");
        }
        if (StringUtils.isBlank(param.getId())) {
            throw new GlobalException("订单ID不能为空！");
        }
        return orderService.importPatientBatch(param);
    }


    /**
     * 获取所有的用户
     *
     * @param key
     * @return
     */
    @GetMapping("/getAllUserSql2")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取所有的用户", notes = "获取所有的用户")
    @ApiImplicitParam(name = "key", value = "输入码或姓名")
    public R<List<AllUserDataVo>> getAllUserSql2(String key) {
        List<AllUserDataVo> list = sysUserService.getAllUserSql2(key);
        return R.ok(list);
    }


    /**
     * 获取分页统计数据
     *
     * @return
     */
    @GetMapping("/getStatistics")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取分页统计数据", notes = "获取分页统计数据")
    public R<StatisticsVo> getStatistics(DbOrderParam param) {
        StatisticsVo vo = orderService.getStatistics(param);
        return R.ok(vo);
    }


    /**
     * 清除
     *
     * @param id
     * @param ids
     * @return
     */
    @DeleteMapping("/removeAll")
    @Log(title = "备单-清除", businessType = BusinessType.INSERT)
    @ApiOperation(value = "清除", notes = "清除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "任务分组ID"),
            @ApiImplicitParam(name = "ids", value = "删除的体检者id")
    })
    public R removeAll(@RequestParam String id, @RequestParam List<String> ids) {
        Boolean b = orderService.removeAll(id, ids);
        return R.toResult(b);
    }


    /**
     * 获取当前选中的已预约用户信息
     *
     * @param patientCode
     * @param type
     * @param autoFill
     * @return
     */
    @GetMapping("/getCustomerData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取当前选中的已预约用户信息", notes = "获取当前选中的已预约用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "type", value = "类型,0全部显示，1显示除去退项的，2显示退项的"),
            @ApiImplicitParam(name = "autoFill", value = "是否补0 ，true是false否")
    })
    public R getCustomerData(String patientCode, String type, String autoFill) {
        HashMap map = orderService.getCustomerData(patientCode, type, autoFill);
        return R.ok(map);
    }


    /**
     * 反收费
     *
     * @param id
     * @return
     */
    @PutMapping("/returnItem")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "反收费", notes = "反收费")
    @ApiImplicitParam(name = "id", value = "id")
    public R returnItem(@RequestParam String id) {
        Boolean b = orderService.returnItem(id);
        return R.toResult(b);
    }

    /**
     * 导出Excel
     *
     * @param response
     * @param param    导出参数
     */
    @PostMapping("/export")
//    @PreAuthorize("@ss.hasPermi('::export')")
    @ApiOperation(value = "导出Excel", notes = "导出Excel")
    @Log(title = "导出Excel", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, DbOrderParam param) throws IOException {
        //导出多sheet
        orderService.getExportData(response,param);
    }


    /**
     * 导出应急导引单
     * @param response
     * @param id
     */
    @PostMapping("/exportGuidanceList")
//    @PreAuthorize("@ss.hasPermi('::export')")
    @ApiOperation(value = "导出应急导引单", notes = "导出应急导引单")
    @Log(title = "导出Excel", businessType = BusinessType.EXPORT)
    public void exportGuidanceList(HttpServletResponse response, String id) throws Exception {
       orderService.exportGuidanceList(response,id);
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
    @ApiOperation(value = "分组-禁检或反禁检", notes = "分组-禁检或反禁检")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "禁检1,反禁检0"),
            @ApiImplicitParam(name = "ids", value = "id集合")
    })
    @Log(title = "分组-禁检或反禁检", businessType = BusinessType.INSERT)
    public R updateGroupLimit(@RequestParam Integer type, @RequestParam List<String> ids) {
        Boolean b = orderService.updateGroupLimit(type, ids);
        return R.toResult(b);
    }





    /**
     * 重新计算总工龄和接害工龄
     *
     * @param patientCodes
     * @return
     */
    @PostMapping("/calculateZglAndJhgl")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "重新计算总工龄和接害工龄", notes = "重新计算总工龄和接害工龄")
    public R calculateZglAndJhgl(@RequestBody List<String> patientCodes) {
        Boolean b = orderService.calculateZglAndJhgl(patientCodes);
        return R.toResult(b);
    }




    /**
     * 分页查询备单订单列表
     *
     * @param ddh 订单号
     * @return 所有数据
     */
    @GetMapping("/appointmentSMSByDddh")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "发送预约短信", notes = "发送预约短信")
    public R<Boolean> appointmentSMSByDddh(String ddh) {
        Boolean b = orderService.appointmentSMSByDddh(ddh);
        return R.ok(b);
    }




    /**
     * 获取分组下相应的人员信息
     *
     * @param param
     * @param param
     * @return
     */
    @GetMapping("/getPatientDataList")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取分组下相应的人员信息(不分页)", notes = "获取分组下相应的人员信息(不分页)")
    public R<List<OrderPaDataVo>> getPatientDataList(OrderPaDataParam param) {
        List<OrderPaDataVo> list = orderService.getPatientDataList(param);
        return R.ok(list);
    }






    /**
     * 检查名单
     *
     * @param param
     * @return
     */
    @PostMapping("/checkList")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "检查名单", notes = "检查名单")
    public void checkList(HttpServletResponse response, CheckListParam param) {
        if (Objects.isNull(param.getFile())) {
            throw new GlobalException("请选择上传文件！");
        }
        String fileName = param.getFile().getOriginalFilename();
        //判断文件后缀
        String endSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        if (!endSuffix.toLowerCase().endsWith("xls") && !endSuffix.toLowerCase().endsWith("xlsx")) {
            throw new GlobalException("请选择正确的文件类型，必须是以.xls或.xlsx结尾！");
        }
        if (StringUtils.isBlank(param.getId())) {
            throw new GlobalException("订单ID不能为空！");
        }
        //导出excle
        List<CheckListDto> list = orderService.checkList(param);
        // TODO: 2024/5/30 只导出错误信息不为空的
        List<CheckListDto> filteredList = list.stream()
                .filter(check -> StringUtils.isNotEmpty(check.getCheckNote()))
                .collect(Collectors.toList());
        ExcelUtil<CheckListDto> util = new ExcelUtil<CheckListDto>(CheckListDto.class);
        util.exportExcel(response, filteredList, "名单校验");
    }


    /**
     * 校正体检者收费方式
     *
     * @param idPayway
     * @return
     */
    @PostMapping("/checkPeispatient")
    @ApiOperation(value = "校正体检者收费方式", notes = "校正体检者收费方式")
    public R checkPeispatient(String idPayway) {
        Boolean b = orderService.checkPeispatient(idPayway);
        return R.ok(b);
    }




    /**
     * 保存
     *
     * @param patientCodes
     * @return
     */
    @PostMapping("/generatePatientChangeMain")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "生成体检者主表", notes = "生成体检者主表")
    public R generatePatientChangeMain(List<String> patientCodes) {
        Boolean b = orderService.generatePatientChangeMain(patientCodes);
        return R.toResult(b);
    }



    /**
     * 重新计算年龄
     *
     * @param ddhs
     * @return
     */
    @PostMapping("/calculateAge")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "重新计算年龄", notes = "重新计算年龄")
    public R calculateAge(@RequestBody List<String> ddhs) {
        Boolean b = orderService.calculateAge(ddhs);
        return R.toResult(b);
    }



    /**
     * 批量添加用户和分中心
     *
     * @return
     * @throws SQLException
     */
    @PostMapping("/addSysUserBranch")
    @ApiOperation(value = "批量添加用户和分中心", notes = "批量添加用户和分中心")
    public R addSysUserBranch(@RequestBody AddSysUserBranchParam param){
        return R.ok(sysUserBranchService.addSysUserBranch(param));
    }




    /**
     * 设置工种
     *
     * @param id
     * @param ids
     * @return
     */
    @PutMapping("/setRepeated")
    @ApiOperation(value = "设为可重复", notes = "设为可重复")
    @ApiImplicitParam(name = "ids", value = "分组id多个")
    public R setRepeated(@RequestParam List<String> ids) {
        Boolean b = orderService.setRepeated(ids);
        return R.ok(b);
    }

    /**
     * 设置工种
     *
     * @param id
     * @param ids
     * @return
     */
    @PostMapping("/batchAddItems")
    @ApiOperation(value = "批量加项", notes = "批量加项")
    public R batchAddItems(@RequestBody BatchAddItemsParam param) {
        Boolean b = orderService.batchAddItems(param);
        return R.ok(b);
    }




    /**
     * 查看套餐分页
     *
     * @param pageParamSimple 分页参数
     * @param idOrgRw
     * @return
     */
    @GetMapping("/getGroupDataPage")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查看套餐分页", notes = "查看套餐分页")
    @ApiImplicitParam(name = "idOrgRw", value = "体检团体任务ID")
    public R getGroupDataPage(PageParamSimple pageParamSimple, String idOrgRw, String tjtcmc) {
        if (StringUtils.isNotEmpty(tjtcmc)){
            tjtcmc = tjtcmc.trim();
        }
        Map resultMap = orderService.getGroupData(idOrgRw, tjtcmc);

        // 从返回的 Map 中获取数据
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) resultMap.get("data");
        Object totalObj = resultMap.get("total");
        long total = totalObj != null ? ((Number) totalObj).longValue() : (dataList != null ? dataList.size() : 0);

        // 分页处理
        if (pageParamSimple.getCurrent()==0) pageParamSimple.setCurrent(1);
        long start = (pageParamSimple.getCurrent() - 1) * pageParamSimple.getSize();
        long end = Math.min(start + pageParamSimple.getSize(), total);

        List<Map<String, Object>> pagedList;
        if (dataList == null || dataList.isEmpty() || start >= total) {
            pagedList = Collections.emptyList();
        } else {
            pagedList = new ArrayList<>(dataList.subList((int) start, (int) end));
        }
        // 创建分页结果
        IPage<Map<String, Object>> resultPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageParamSimple.getCurrent(), pageParamSimple.getSize(), total);
        resultPage.setRecords(new ArrayList<>(pagedList));

        return R.ok(resultPage);
    }
}

