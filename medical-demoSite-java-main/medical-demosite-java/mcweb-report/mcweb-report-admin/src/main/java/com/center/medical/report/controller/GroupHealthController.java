package com.center.medical.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.report.bean.model.BallCheckReport;
import com.center.medical.report.bean.model.SamplePerson;
import com.center.medical.report.bean.param.GHSaveDataParam;
import com.center.medical.report.bean.param.GroupHealthParam;
import com.center.medical.report.bean.param.LoadSampleParam;
import com.center.medical.report.bean.vo.BallCheckReportVo;
import com.center.medical.report.bean.vo.GHExportDataVo;
import com.center.medical.bean.vo.SamplePersonVo;
import com.center.medical.report.service.BallCheckReportService;
import com.center.medical.report.service.SamplePersonService;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.service.CreateorderService;
import com.center.medical.sellcrm.service.SellcustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 *  职业/监控功能团检样本
 *
 * @author 浮俊杰
 * @since 2022-12-16 20:37:35
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "团检报告-职业健康团检样本")
@RequestMapping("/group/groupHealth")
public class GroupHealthController extends BaseController {
    /**
     * 服务对象
     */
    private final BallCheckReportService ballCheckReportService;
    private final SamplePersonService samplePersonService;
    private final MapperFacade mapperFacade;
    private final CreateorderService createorderService;
    private final SellcustomerService sellcustomerService;

    /**
     * 分页查询团检报告主表
     *
     * @param pageParamSimple 分页参数
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/loadBallCheckData")
    @ApiOperation(value = "分页查询数据", notes = "分页查询数据")
    public R<IPage<BallCheckReport>> getListData(PageParamSimple pageParamSimple, GroupHealthParam param) {
        PageParam<BallCheckReport> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.ballCheckReportService.loadBallCheckData(page, param));
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<BallCheckReport> selectOne(@PathVariable String id) {
        return R.ok(ballCheckReportService.getInfoById(id));
    }


    /**
     * 左侧新增修改数据
     *
     * @param ballCheckReportVo 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiOperation(value = "增加/编辑", notes = "添加团检报告主表")
    @Log(title = "团检报告主表", businessType = BusinessType.INSERT)
    public R saveBallCheckData(@RequestBody BallCheckReportVo ballCheckReportVo) {
        return R.ok(this.ballCheckReportService.saveBallCheckData(ballCheckReportVo));
    }

    /**
     * 加载右侧人员信息
     *
     * @return 查询结果
     */
    @GetMapping("/loadSamplePersonData")
    @ApiOperation(value = "加载右侧人员信息", notes = "加载右侧人员信息")
    public R<IPage<SamplePersonVo>> loadSamplePersonData(PageParamSimple pageParamSimple,LoadSampleParam param){
        PageParam<SamplePersonVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.samplePersonService.loadSamplePersonData(page,param));
    }

    /**
     * 加载右侧人员信息
     *
     * @param id
     * @return 查询结果
     */
    @GetMapping("/addpeople/{id}")
    @ApiOperation(value = "加入人员", notes = "加入人员")
    @ApiImplicitParam(name = "id", value = "ID")
    public R addpeople(@PathVariable String id) throws ParseException {
        new Thread(() -> ballCheckReportService.addpeople(id)).start();
        return R.ok("添加中,请耐心等待!");
    }

    /**
     * 删除左侧选中信息
     *
     * @param ids
     * @return 查询结果
     */
    @GetMapping("/remove/{ids}")
    @ApiOperation(value = "删除", notes = "删除左侧选中信息")
    @ApiImplicitParam(name = "ids", value = "ids")
    public R remove(@PathVariable String ids) {
        return R.ok(this.ballCheckReportService.removeReport(ids));
    }

    /**
     * 右侧名称查询
     *
     * @param groupId,orderId,reportId
     * @return 查询结果
     */
    @GetMapping("/addSamplePersonData")
    @ApiOperation(value = "右侧名称查询", notes = "右侧名称查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "组ID"),
            @ApiImplicitParam(name = "idOrgid", value = "订单ID(没用到)"),
            @ApiImplicitParam(name = "reportId", value = "报告ID"),
            @ApiImplicitParam(name = "key", value = "体检号或体检者名称"),
            @ApiImplicitParam(name = "flag", value = "添patientcode或patientname")
    })
    public R addSamplePersonData(String groupId, String idOrgid, String reportId,
                                  String key, String flag){
        return R.ok(this.ballCheckReportService.addSamplePersonData(groupId,idOrgid,reportId,key,flag));
    }

    /**
     * 保存右侧人员
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveData")
    @ApiOperation(value = "保存", notes = "保存右侧人员")
    public R saveData(@RequestBody GHSaveDataParam param){
        return R.ok(this.ballCheckReportService.saveData(param));
    }

    /**
     * 右侧导出
     *
     * @param groupId,reportId
     * @return 新增结果
     */
    @PostMapping("/export/{groupId}/{reportId}")
    @ApiOperation(value = "导出", notes = "右侧导出")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "组ID"),
            @ApiImplicitParam(name = "reportId", value = "报告ID")
    })
    public void export(HttpServletResponse response, @PathVariable String groupId, @PathVariable String reportId){
        List<GHExportDataVo> list = ballCheckReportService.export(groupId, reportId);
        ExcelUtil<GHExportDataVo> util = new ExcelUtil<GHExportDataVo>(GHExportDataVo.class);
        util.exportExcel(response, list, "样本人员明细");
    }

    /**
     * 联动团检报告订单数据
     *
     * @param groupId
     * @return 查询结果
     */
    @GetMapping("/addBallCheckReportOrderData/{groupId}")
    @ApiOperation(value = "联动团检报告订单数据", notes = "联动团检报告订单数据")
    @ApiImplicitParam(name = "groupId", value = "组ID")
    public R addBallCheckReportOrderData(@PathVariable String groupId){
        return R.ok(this.ballCheckReportService.addBallCheckReportOrderData(groupId));
    }

    /**
     * 联动团检报告人员样本表数据
     *
     * @param id
     * @return 查询结果
     */
    @GetMapping("/loadSamplePersonViewData/{id}")
    @ApiOperation(value = "联动团检报告订单数据", notes = "联动团检报告订单数据")
    @ApiImplicitParam(name = "id", value = "ID")
    public R loadSamplePersonViewData(@PathVariable String id){
        return R.ok(this.ballCheckReportService.loadSamplePersonViewData(id));
    }


    /**
     * 联动团检任务数据
     * @param orderId
     * @return
     */
    @GetMapping("/addBallCheckReportData/{orderId}")
    @ApiOperation(value = "联动团检任务数据", notes = "联动团检任务数据")
    @ApiImplicitParam(name = "orderId", value = "订单号(ddh)")
    public R addBallCheckReportData(@PathVariable("orderId") String orderId){
        //线上部署，不再查询团体任务表
        //订单表
        Createorder order = createorderService.getOne(new QueryWrapper<Createorder>().eq("ddh",orderId));
        if(ObjectUtils.isEmpty(order)){
            throw new ServiceException("无此订单号");
        }
        HashMap map = new HashMap();
        map.put("idOrg", order.getKhdwmcid());
        map.put("ddh", order.getDdh());
        map.put("id", order.getId());
        // 客户表
        Sellcustomer customer = sellcustomerService.getInfoById(order.getKhdwmcid());
        map.put("idOrgclass", customer.getTjttlx());
        map.put("orgName",customer.getKhdwmc());
        return R.ok(map);
    }


    /**
     * 移除样本体检者
     * @param ids
     * @return
     */
    @DeleteMapping("/removePersons/{ids}")
    @ApiOperation(value = "移除样本体检者", notes = "移除样本体检者")
    @ApiImplicitParam(name = "ids", value = "ids")
    public R removePersons(@PathVariable List<String> ids) {
        boolean b = samplePersonService.remove(new QueryWrapper<SamplePerson>().in("id", ids));
        return R.toResult(b);
    }









}

