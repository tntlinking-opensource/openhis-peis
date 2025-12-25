package com.center.medical.outreach.pf.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.param.QuestionnaireParam;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.dto.*;
import com.center.medical.reception.bean.param.MyOrderParam;
import com.center.medical.reception.bean.param.UnitReservationParam;
import com.center.medical.reception.service.NewReservationService;
import com.center.medical.reservation.bean.dto.AppointmentDto;
import com.center.medical.reservation.bean.dto.QueryUnitParam;
import com.center.medical.reservation.service.ReservationOpenApiService;
import com.center.medical.reservation.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 查询手机报告列表
 * 该拦截层之作权限数据认证和数据消毒处理，不对任何业务处理
 *
 * @author 路飞船长
 * @since 2023-02-02 15:14:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "新版预约标准数据接口")
@RequestMapping("/open/api/v2/newReservation")
public class NewReservationController extends BaseController {


    private final NewReservationService newReservationService;
    private final ReservationService reservationService;
    private final ReservationOpenApiService reservationOpenApiService;

    /**
     * 首页预约列表
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/homePageList")
    @ApiOperation(value = "首页预约列表", notes = "首页预约列表")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R getGroupOrderList(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        String phone = (String) dataStr;
        log.info("请求参数：{}", phone);
        List<HomePageListDto> list = newReservationService.getHomePageList(phone);
        return R.ok(JSONUtil.toJsonStr(list));

    }



    /**
     * 获取用户档案信息
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/userIdcard")
    @ApiOperation(value = "获取用户档案信息", notes = "获取用户档案信息")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R userIdcard(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        String phone = (String) dataStr;
        log.info("请求参数：{}", phone);
        List<UserIdcardDto> list = newReservationService.getUserIdcard(phone);
        return R.ok(JSONUtil.toJsonStr(list));

    }



    /**
     * 获取体检者数据
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/getNewReData")
    @ApiOperation(value = "获取体检者数据", notes = "获取体检者数据")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R getNewReData(HttpServletRequest request) {
        String dataStr = (String)request.getAttribute("dataStr");
        QuestionnaireParam param = JSONUtil.toBean(dataStr, QuestionnaireParam.class);
        log.info("请求参数：{}", param);
        Boolean b = newReservationService.checkPatientcode(param.getPatientcode(),param.getPhone());
        if (b){
            GetNewReDataDto dto = newReservationService.getNewReData(param.getPatientcode());
            return R.ok(JSONUtil.toJsonStr(dto));
        }else {
            return R.fail();
        }
    }



    /**
     * 立即预约
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/appointmentNow")
    @ApiOperation(value = "立即预约", notes = "立即预约")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R appointmentNow(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        log.info("接收预约申请信息,data：{}", dataStr);
        AppointmentDto data = JSONUtil.toBean(dataStr, AppointmentDto.class);
        Boolean b = newReservationService.checkPatientcode(data.getPatientcode(),data.getMobile());
        if (b){
            String reservationNo = newReservationService.appointmentNow(data);
            return R.ok(reservationNo);
        }else {
            return R.fail();
        }
    }





    /**
     * 单位预约查询单位
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/queryUnit")
    @ApiOperation(value = "单位预约查询单位", notes = "单位预约查询单位")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R queryUnit(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        QueryUnitParam data = JSONUtil.toBean(dataStr, QueryUnitParam.class);
        log.info("请求参数：{}", data);
        PageParam<QueryUnitDto> page = new PageParam<>();
        page.setSize(data.getSize());
        page.setCurrent(data.getCurrent());

        IPage<QueryUnitDto> iPage = newReservationService.queryUnit(page,data.getName());
        List<QueryUnitDto> list = iPage.getRecords();
        return R.ok(JSONUtil.toJsonStr(list));

    }



    /**
     * 单位预约提交
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/unitReservation")
    @ApiOperation(value = "单位预约提交", notes = "单位预约提交")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R unitReservation(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        UnitReservationParam param = JSONUtil.toBean(dataStr, UnitReservationParam.class);
        log.info("请求参数：{}", param);
        String patientcode = newReservationService.unitReservation(param);
        return R.ok(patientcode);
    }



    /**
     * 帮人预约
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/helpAppoint")
    @ApiOperation(value = "帮人预约", notes = "帮人预约")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R helpAppoint(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        String phone = (String) dataStr;
        List<HelpAppointDto> list = newReservationService.helpAppoint(phone);
        return R.ok(JSONUtil.toJsonStr(list));
    }




    /**
     * 我的订单
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/myOrder")
    @ApiOperation(value = "我的订单", notes = "我的订单")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R myOrder(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        MyOrderParam param = JSONUtil.toBean(dataStr, MyOrderParam.class);
        PageParam<MyOrderDto> page = new PageParam<>();
        page.setSize(param.getSize());
        page.setCurrent(param.getCurrent());

        IPage<MyOrderDto> iPage = newReservationService.myOrder(page,param);
        return R.ok(JSONUtil.toJsonStr(iPage));
    }




    /**
     * 订单角标
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/orderMarkers")
    @ApiOperation(value = "订单角标", notes = "订单角标")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R orderMarkers(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        String phone = (String) dataStr;
        OrderMarkersDto dto = newReservationService.orderMarkers(phone);
        return R.ok(JSONUtil.toJsonStr(dto));
    }
}

