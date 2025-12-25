package com.center.medical.outreach.pf.controller;

import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.reservation.bean.dto.IntegratedReservationAvailableDto;
import com.center.medical.reservation.bean.dto.IntegratedReservationBranchListDto;
import com.center.medical.reservation.bean.dto.IntegratedReservationStatusDto;
import com.center.medical.reservation.service.IntegratedReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xhp
 * @since 2023-12-27 9:29
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "新老系统集成预约接口")
@RequestMapping("/open/api/v2/integratedReservation")
public class IntegratedReservationController extends BaseController {
    private final IntegratedReservationService integratedReservationService;

    /**
     * 根据第三方套餐id获取可预约机构门店列表
     *
     * @param request
     * @return
     */
    @PostMapping("/branch/list")
    @ApiOperation(value = "根据第三方套餐id获取可预约机构门店列表", notes = "根据第三方套餐id获取可预约机构门店列表")
    public R<List<IntegratedReservationBranchListDto>> getBranchList(HttpServletRequest request){
        String dataStr = (String) request.getAttribute("dataStr");
        return R.ok(integratedReservationService.getBranchList(dataStr));
    }

    /**
     * 获取可预约时间段列表
     *
     * @param request
     * @return
     */
    @PostMapping("/getAvailableNums")
    @ApiOperation(value = "获取可预约时间段列表", notes = "获取可预约时间段列表")
    public R<List<IntegratedReservationAvailableDto>> getAvailableNums(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        return R.ok(integratedReservationService.getAvailableNums(dataStr));
    }

    /**
     * 预约申请
     * @param request
     * @return
     */
    @PostMapping("/apply")
    @ApiOperation(value = "预约申请", notes = "预约申请")
    @RepeatSubmit
    public R<String> apply(HttpServletRequest request){
        String data = (String) request.getAttribute("dataStr");
        return R.ok(integratedReservationService.apply(data));
    }

    /**
     * 取消预约
     * @param request
     * @return
     */
    @PostMapping("/cancel")
    @ApiOperation(value = "预约取消", notes = "预约取消")
    public R<String> cancel(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        integratedReservationService.cancel(dataStr);
        return R.ok();
    }

    /**
     * 根据第三方订单ID获取预约状态
     * @param request
     * @return
     */
    @PostMapping("/status")
    @ApiOperation(value = "根据第三方订单ID获取预约状态", notes = "根据第三方订单ID获取预约状态")
    public R<IntegratedReservationStatusDto> getStatus(HttpServletRequest request){
        String dataStr = (String) request.getAttribute("dataStr");
        return R.ok(integratedReservationService.getStatus(dataStr));
    }
}
