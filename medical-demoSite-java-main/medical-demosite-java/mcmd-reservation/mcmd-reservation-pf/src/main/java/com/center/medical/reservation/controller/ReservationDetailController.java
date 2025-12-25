package com.center.medical.reservation.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.reservation.bean.dto.RGroupData;
import com.center.medical.reservation.bean.dto.ReservationData;
import com.center.medical.reservation.bean.dto.ReservationGroupData;
import com.center.medical.reservation.bean.dto.ReservationTotalDto;
import com.center.medical.reservation.bean.model.ReservationSetting;
import com.center.medical.reservation.bean.param.ReservationParam;
import com.center.medical.reservation.bean.param.ReservationSettingGroupParam;
import com.center.medical.reservation.bean.param.RgListParam;
import com.center.medical.reservation.service.ReservationGroupService;
import com.center.medical.reservation.service.ReservationService;
import com.center.medical.reservation.service.ReservationSettingService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 体检预约-预约记录控制层
 *
 * @author ay
 * @since 2023-03-18 08:58:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 1)
@Api(tags = "体检预约详情")
@RequestMapping("/reservation/detail")
public class ReservationDetailController extends BaseController {
    /**
     * 服务对象
     */
    private final ReservationService reservationService;
    private final ReservationSettingService reservationSettingService;
    private final MapperFacade mapperFacade;
    private final ReservationGroupService reservationGroupService;


    /**
     * 分页查询预约详情列表
     *
     * @param pageParamSimple 分页参数
     * @param param           筛选条件
     * @return
     */
    @PostMapping("/getReservationTotal")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取预约详情统计列表", notes = "获取预约详情统计列表")
    public R<IPage<ReservationTotalDto>> getReservationTotal(PageParamSimple pageParamSimple, @Validated ReservationSettingGroupParam param) {
        PageParam<ReservationSetting> page = mapperFacade.map(pageParamSimple, PageParam.class);
        param.setStatusList(Arrays.asList(1, 2));
        IPage<ReservationTotalDto> grouppage = this.reservationSettingService.groupPage(page, param);
        return R.ok(grouppage);
    }


    /**
     * 人员预约信息导出
     *
     * @param response
     * @param param
     */
    @Log(title = "人员预约信息导出", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "人员预约信息导出", notes = "人员预约信息导出")
    @PostMapping("/personalExport")
    public void personalExport(HttpServletResponse response, ReservationParam param) {
        List<ReservationData> list = this.reservationService.getExportData(param);
        ExcelUtil<ReservationData> util = new ExcelUtil<>(ReservationData.class);
        String date = DateUtil.format(param.getReservationDate(), "yyyy-MM-dd");
//        String endDate = DateUtil.format(param.getEndDate(), "yyyy-MM-dd");
        util.exportExcel(response, list, date + "人员预约信息", date + "人员预约信息");
//        if (startDate.equals(endDate)) {
//        } else {
//            util.exportExcel(response, list, startDate + "至" + endDate + "人员预约信息", startDate + "至" + endDate + "人员预约信息");
//        }
    }

    /**
     * 日期预约信息导出
     *
     * @param response
     * @param param
     */
    @Log(title = "日期预约信息导出", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "日期预约信息导出", notes = "日期预约信息导出")
    @PostMapping("/exportDetailTotal")
    public void exportDetailTotal(HttpServletResponse response, ReservationSettingGroupParam param) {

        param.setStatusList(Arrays.asList(1, 2));
        List<ReservationTotalDto> list = this.reservationSettingService.getExportDetailData(param);

        ExcelUtil<ReservationTotalDto> util = new ExcelUtil<>(ReservationTotalDto.class);
        String startDate = DateUtil.format(param.getStartDate(), "yyyy-MM-dd");
        String endDate = DateUtil.format(param.getEndDate(), "yyyy-MM-dd");
        if (startDate.equals(endDate)) {
            util.exportExcel(response, list, startDate + "预约信息", startDate + "预约信息");
        } else {
            util.exportExcel(response, list, startDate + "至" + endDate + "预约信息", startDate + "至" + endDate + "预约信息");
        }
    }

    /**
     * 团体预约信息导出
     *
     * @param response
     * @param param
     */
    @Log(title = "团体预约信息导出", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "团体预约信息导出", notes = "团体预约信息导出")
    @PostMapping("/groupExport")
    public void groupExport(HttpServletResponse response, RgListParam param) {
        List<ReservationGroupData> list = this.reservationGroupService.getExportData(param);
        ExcelUtil<ReservationGroupData> util = new ExcelUtil<>(ReservationGroupData.class);
        String startDate = DateUtil.format(param.getStartDate(), "yyyy-MM-dd");
        String endDate = DateUtil.format(param.getEndDate(), "yyyy-MM-dd");
        util.exportExcel(response, list, startDate + "团体预约信息", startDate + "团体预约信息");
//        if (startDate.equals(endDate)) {
//        } else {
//            util.exportExcel(response, list, startDate + "至" + endDate + "团体预约信息", startDate + "至" + endDate + "团体预约信息");
//        }
    }

    /**
     * 团体预约信息按时间段导出
     *
     * @param response
     * @param param
     */
    @Log(title = "团体预约信息按时间段导出", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "团体预约信息按时间段导出", notes = "团体预约信息按时间段导出")
    @PostMapping("/groupExportDuring")
    public void groupExportDuring(HttpServletResponse response, RgListParam param) {
        List<RGroupData> list = this.reservationService.getGroupExportData(param);
        ExcelUtil<RGroupData> util = new ExcelUtil<>(RGroupData.class);
        String startDate = DateUtil.format(param.getStartDate(), "yyyy-MM-dd");
        String endDate = DateUtil.format(param.getEndDate(), "yyyy-MM-dd");
        if (startDate.equals(endDate)) {
            util.exportExcel(response, list, startDate + "团体预约信息", startDate + "团体预约信息");
        } else {
            util.exportExcel(response, list, startDate + "至" + endDate + "团体预约信息", startDate + "至" + endDate + "团体预约信息");
        }

//        SELECT
//        rv.branch_id,
//                sb.fzx,
//                rs.am_or_pm,
//                DATE ( rv.reservation_date ) AS reservation_date,
//        rv.order_num,
//                co.ddmc,
//                COUNT(*) AS total_count
//        FROM
//        md_reservation rv
//        LEFT JOIN md_reservation_setting rs ON rv.time_id = rs.id
//        LEFT JOIN sys_branch sb ON rv.branch_id = sb.branch_id
//        LEFT JOIN md_createorder co ON rv.order_num = co.ddh
//        WHERE
//        rv.order_num IS NOT NULL
//        GROUP BY
//        rv.order_num,
//                DATE (rv.reservation_date),
//                rs.am_or_pm
    }
}

