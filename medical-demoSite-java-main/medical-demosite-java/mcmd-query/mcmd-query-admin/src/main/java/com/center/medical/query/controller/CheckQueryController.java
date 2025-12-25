package com.center.medical.query.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.param.ConfirmOrderParam;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.query.service.CheckQueryService;
import com.center.medical.reception.bean.param.PaForReParam;
import com.center.medical.reception.bean.vo.PaForReVo;
import com.center.medical.reception.service.RegisterService;
import com.center.medical.report.bean.param.IReportParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 登记信息查询(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-08 09:31:50
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "登记信息查询")
@RequestMapping("query/checkQuery")
public class CheckQueryController extends BaseController {
    /**
     * 服务对象
     */
    private final CheckQueryService checkQueryService;
    private final MapperFacade mapperFacade;
    private final RegisterService registerService;


    /**
     * 最近体检人员列表
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getPatientForRegister")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "最近体检人员列表", notes = "获取已登记的体检人员信息")
    public R<IPage<PaForReVo>> getPatientForRegister(PageParamSimple pageParamSimple, PaForReParam param) {
        PageParam<PaForReVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<PaForReVo> iPage = registerService.getPatientForRegister(page, param);
        return R.ok(iPage);
    }


    /**
     * 终审交接
     *
     * @param ids id集合
     * @return 修改结果
     */
    @PutMapping("/zsjj")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "临时报告终审交接", notes = "临时报告终审交接")
    @ApiImplicitParam(name = "ids", value = "id集合")
    @Log(title = "QT体检者表", businessType = BusinessType.UPDATE)
    public R zsjj(@RequestParam List<String> ids) {
        return R.toResult(this.checkQueryService.zsjj(ids));
    }


    /**
     * 终审交接
     *
     * @param ids id集合
     * @return 修改结果
     */
    @PutMapping("/updateclose")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "结案", notes = "结案")
    @ApiImplicitParam(name = "ids", value = "id集合")
    @Log(title = "QT体检者表", businessType = BusinessType.UPDATE)
    public R updateclose(@RequestParam List<String> ids) {
        return R.toResult(this.checkQueryService.updateclose(ids));
    }


    /**
     * 旧案召回
     *
     * @param ids
     * @return
     */
    @PutMapping("/reSaveHistory")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "旧案召回", notes = "旧案召回")
    @ApiImplicitParam(name = "ids", value = "id集合")
    @Log(title = "QT体检者表", businessType = BusinessType.UPDATE)
    public R reSaveHistory(@RequestParam List<String> ids) {
        return R.toResult(this.checkQueryService.reSaveHistory(ids));
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出登记信息列表")
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @Log(title = "登记信息列表", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, PaForReParam param) {
        List<PaForReVo> list = registerService.getPaExportData(param);
        ExcelUtil<PaForReVo> util = new ExcelUtil<PaForReVo>(PaForReVo.class);
        util.exportExcel(response, list, "登记信息列表");
    }


    /**
     * 加急
     *
     * @param ids
     * @return
     */
    @PutMapping("/jiaji")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "加急", notes = "加急")
    @ApiImplicitParam(name = "ids", value = "id集合")
    @Log(title = "QT体检者表", businessType = BusinessType.UPDATE)
    public R jiaji(@RequestParam List<String> ids) {
        return R.toResult(this.checkQueryService.jiaji(ids));
    }


    /**
     * 临时报告生成
     * @param param
     * @return
     */
    @PostMapping("/createTemp")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "临时报告生成", notes = "临时报告生成")
    public R<Boolean> createTemp(IReportParam param) {
        Boolean b = checkQueryService.createTemp(param);
        return R.ok(b);
    }



    /**
     * 到检确认
     * @param param
     * @return
     */
    @PostMapping("/confirmOrder")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "到检确认", notes = "到检确认")
    public R<Boolean> confirmOrder(ConfirmOrderParam param) {
        Boolean b = checkQueryService.confirmOrder(param);
        return R.toResult(b);
    }


}

