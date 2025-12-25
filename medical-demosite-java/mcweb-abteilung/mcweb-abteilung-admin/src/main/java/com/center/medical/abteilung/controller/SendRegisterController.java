package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.model.OutsideChargeItem;
import com.center.medical.abteilung.bean.param.SendRegisterParam;
import com.center.medical.abteilung.bean.param.SrSaOrUpParam;
import com.center.medical.abteilung.bean.vo.OutsideVo;
import com.center.medical.abteilung.service.OutsideChargeItemService;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.bean.vo.OSItemDataVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.vo.WsjgDataVo;
import com.center.medical.data.service.WsjgService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.bean.vo.LqrDataVo;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysUserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
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
import java.util.Map;

/**
 * 外送管理-外送登记表控制层
 *
 * @author ay
 * @since 2023-01-04 09:00:50
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "外送管理-外送登记")
@RequestMapping("/outside/sendRegister")
public class SendRegisterController extends BaseController {
    /**
     * 服务对象
     */
    private final OutsideChargeItemService outsideChargeItemService;
    private final MapperFacade mapperFacade;
    private final ISysUserService sysUserService;
    private final WsjgService wsjgService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final ISysBranchService iSysBranchService;


    /**
     * 【外送管理-外送登记】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【外送管理-外送登记】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/outside/SendRegister/page", "09.科室系统->科室管理-外送管理-外送登记->分页查询", null),
                new InterfaceVo("保存", "POST", "/outside/SendRegister/saveOrUpdate", "09.科室系统->科室管理-外送管理-外送登记->保存", null),
                new InterfaceVo("删除", "DELETE", "/outside/SendRegister/{ids}", "09.科室系统->科室管理-外送管理-外送登记->删除", null),
                new InterfaceVo("外送机构下拉", "GET", "/outside/SendRegister/getWsjgData", "09.科室系统->科室管理-外送管理-外送登记->外送机构下拉", null),
                new InterfaceVo("项目列表", "GET", "/outside/SendRegister/getItemData", "09.科室系统->科室管理-外送管理-外送登记->项目列表", null),
                new InterfaceVo("承送人名处搜索", "GET", "/outside/SendRegister/getLqrData", "09.科室系统->科室管理-外送管理-外送登记->承送人名处搜索", null),
                new InterfaceVo("导出", "POST", "/outside/SendRegister/export", "09.科室系统->科室管理-外送管理-外送登记->导出", null),
                new InterfaceVo("新增外送登记-上方数据", "GET", "/outside/SendRegister/getPatientData", "09.科室系统->科室管理-外送管理-外送登记->新增外送登记-上方数据", null)
        );
        return R.ok(new FunctionVo("09.科室系统", "外送管理-外送登记", interfaceVos, "09.科室系统"));
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
    @ApiOperation(value = "分页查询", notes = "分页查询KS外送项目表")
    public R<IPage<OutsideVo>> getPage(PageParamSimple pageParamSimple, SendRegisterParam param) {
        PageParam<OutsideChargeItem> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.outsideChargeItemService.getPage(page, param));
    }


    /**
     * 新增外送登记-保存
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @RepeatSubmit(interval = 3000, message = "正在处理，请稍等")
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "保存", notes = "新增外送登记-保存")
    @ApiOperationSupport(ignoreParameters = {"outsideChargeItem.id"})
    public R insert(@RequestBody SrSaOrUpParam param) {
        return R.toResult(this.outsideChargeItemService.saOrUp(param));
    }


    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "假删")
    @Log(title = "KS外送项目表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        OutsideChargeItem outsideChargeItem = new OutsideChargeItem();
        ////将isDelete设置为1，为删除
        outsideChargeItem.setIsDelete(1);
        boolean b = outsideChargeItemService.update(outsideChargeItem, new UpdateWrapper<OutsideChargeItem>().in("id", ids));
        return R.toResult(b);
    }


    /**
     * 外送机构下拉
     *
     * @param pageParamSimple
     * @param srm
     * @return
     */
    @GetMapping("/getWsjgData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "外送机构下拉", notes = "外送机构下拉")
    @ApiImplicitParam(name = "srm", value = "输入码")
    public R getWsjgData(PageParamSimple pageParamSimple, String srm) {
        PageParam<WsjgDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<WsjgDataVo> iPage = wsjgService.getWsjgData(page, srm);
        return R.ok(iPage);
    }


    /**
     * 项目列表
     *
     * @param pageParamSimple
     * @param patientcode
     * @return
     */
    @GetMapping("/getItemData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "外送登记项目列表", notes = "在新增外送登记处，下方搜索出来的项目列表")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<IPage<OSItemDataVo>> getItemData(PageParamSimple pageParamSimple, String patientcode) {
        PageParam<OSItemDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //补全体检号 补0
        if (ObjectUtils.isNotEmpty(patientcode)) {
            patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
        }
        IPage<OSItemDataVo> iPage = peispatientfeeitemService.getOSItemData(page, patientcode);
        return R.ok(iPage);
    }


    /**
     * 承送人名
     *
     * @param pageParamSimple
     * @param srm
     * @return
     */
    @GetMapping("/getLqrData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "承送人名处搜索", notes = "承送人名处搜索")
    @ApiImplicitParam(name = "srm", value = "输入码")
    public R<IPage<LqrDataVo>> getLqrData(PageParamSimple pageParamSimple, String srm) {
        PageParam<LqrDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<LqrDataVo> iPage = sysUserService.getLqrData(page, srm);
        return R.ok(iPage);
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "外送登记", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SendRegisterParam param) {
        List<OutsideVo> list = outsideChargeItemService.findList(param);
        ExcelUtil<OutsideVo> util = new ExcelUtil<OutsideVo>(OutsideVo.class);
        util.exportExcel(response, list, "外送登记");
    }


    /**
     * 新增外送登记-上方数据
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/getPatientData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "新增外送登记-上方数据", notes = "新增外送登记-上方数据")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<Map<String, Object>> getPatientData(String patientcode) {
        //补全体检号 补0
        if (ObjectUtils.isNotEmpty(patientcode)) {
            patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
        }
        Map<String, Object> vo = outsideChargeItemService.getPatientData(patientcode);
        return R.ok(vo);
    }


}

