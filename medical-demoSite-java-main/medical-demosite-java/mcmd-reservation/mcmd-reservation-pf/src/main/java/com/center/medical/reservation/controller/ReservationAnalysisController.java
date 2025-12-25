package com.center.medical.reservation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelExp;
import com.center.medical.common.utils.poi.ExcelUtilManySheet;
import com.center.medical.reservation.bean.param.RAExportParam;
import com.center.medical.reservation.bean.param.ReAnalysisParam;
import com.center.medical.reservation.bean.param.RightPageParam;
import com.center.medical.reservation.bean.vo.RARightPageVo;
import com.center.medical.reservation.bean.vo.RATodayVo;
import com.center.medical.reservation.bean.vo.ReAnalysisVo;
import com.center.medical.reservation.service.ReservationAnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 体检预约记录(Reservation)接口
 *
 * @author ay
 * @since 2024-03-29 08:58:17
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "预约来检情况分析")
@RequestMapping("/reservation/reservationAnalysis")
public class ReservationAnalysisController extends BaseController {
    /**
     * 服务对象
     */
    private final ReservationAnalysisService reservationAnalysisService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询体检预约记录")
    public R<IPage<ReAnalysisVo>> getPage(PageParamSimple pageParamSimple, ReAnalysisParam param) {
        PageParam<ReAnalysisVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reservationAnalysisService.getPage(page, param));
    }


    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/rightPage")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "右侧分页查询", notes = "右侧分页查询")
    public R<IPage<RARightPageVo>> rightPage(PageParamSimple pageParamSimple, RightPageParam param) {
        PageParam<RARightPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reservationAnalysisService.rightPage(page, param));
    }




    /**
     * 今日情况
     *
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/getToday")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "今日情况", notes = "今日情况")
    public R<RATodayVo> getToday(ReAnalysisParam param) {
        RATodayVo vo = reservationAnalysisService.getToday(param);
        return R.ok(vo);
    }



    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "预约来检情况分析", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "导出", notes = "导出预约来检情况分析")
    @PostMapping("/export")
    public void exportAccountsInfoData(HttpServletResponse response,RAExportParam param) throws IOException {
        ReAnalysisParam leftParam = mapperFacade.map(param, ReAnalysisParam.class);
        //导出多sheet数据
        //左侧1个
        List<ReAnalysisVo> list = reservationAnalysisService.exportPage(leftParam);
        //右侧3个
        RightPageParam param1 = mapperFacade.map(param, RightPageParam.class);
        param1.setType(1);
        List<RARightPageVo> list1 = reservationAnalysisService.exportRightPage(param1);
        param1.setType(2);
        List<RARightPageVo> list2 = reservationAnalysisService.exportRightPage(param1);
        param1.setType(3);
        List<RARightPageVo> list3 = reservationAnalysisService.exportRightPage(param1);
        //导出
        ExcelExp e1 = new ExcelExp("预约整体情况", list, ReAnalysisVo.class);
        ExcelExp e2 = new ExcelExp("已预约来检人", list1, RARightPageVo.class);
        ExcelExp e3 = new ExcelExp("已预约未检人", list2, RARightPageVo.class);
        ExcelExp e4 = new ExcelExp("未预约来检人", list3, RARightPageVo.class);
        List<ExcelExp> mysheet = new ArrayList<ExcelExp>();
        mysheet.add(e1);
        mysheet.add(e2);
        mysheet.add(e3);
        mysheet.add(e4);
        ExcelUtilManySheet<List<ExcelExp>> util2 = new ExcelUtilManySheet<List<ExcelExp>>(mysheet);
        util2.exportExcelManySheet(response, mysheet);
    }

}
