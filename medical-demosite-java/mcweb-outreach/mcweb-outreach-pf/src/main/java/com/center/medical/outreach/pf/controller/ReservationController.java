package com.center.medical.outreach.pf.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.annotation.IpAuth;
import com.center.medical.common.constant.HttpStatus;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.outreach.bean.dto.ReportDto;
import com.center.medical.reception.service.NewReservationService;
import com.center.medical.reservation.bean.dto.AppointmentDto;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.param.AppointmentAvailableParam;
import com.center.medical.reservation.bean.param.ReservationCancelParam;
import com.center.medical.reservation.service.ReservationOpenApiService;
import com.center.medical.reservation.service.ReservationService;
import com.center.medical.system.service.ISysBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * 预约标准数据接口控制层：1.提供可预约时间段和人数 2.接收预约信息 3.返回预约结果 4.预约状态改变通知（到检核销、预约取消） 5.推送体检报告
 * 该拦截层之作权限数据认证和数据消毒处理，不对任何业务处理
 *
 * @author 路飞船长
 * @since 2023-02-02 15:14:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "预约标准数据接口")
@RequestMapping("/open/api/v2/reservation")
public class ReservationController extends BaseController {

    /**
     * 服务对象
     */
    private final ReservationOpenApiService reservationOpenApiService;
    private final ReservationService reservationService;
    private final NewReservationService newReservationService;
    private final ISysBranchService sysBranchService;

    /**
     * 根据手机号获取我的待预约团体订单列表
     *
     * @param request
     * @return
     */
    @PostMapping("/getGroupOrderList")
    @ApiOperation(value = "获取我的待预约团体订单", notes = "根据手机号获取我的待预约团体订单列表")
    public R getGroupOrderList(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        //log.info("根据手机号获取我的待预约团体订单列表,data：{}", dataStr);
        String phone = (String) dataStr;
        if (StringUtils.isBlank(phone)) {
            return R.fail(HttpStatus.BAD_REQUEST, "手机号不能为空！");
        }
        return R.ok(reservationOpenApiService.getGroupOrderList(phone));
        //log.info("请求手机号：{}", paramStr);

        // String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_GROUP_ORDER_PATH;
        // String url = "http://localhost:8090" + Constants.RT_GROUP_ORDER_PATH;
        // Map<String, Object> paramMap = new HashMap<String, Object>() {{
        //     put("phone", paramStr);
        // }};
        // String post = HttpUtil.post(url, paramMap);
        // log.info("请求地址及参数：{}、{}、{}", url, paramMap, post);
        // R result = JSONUtil.toBean(post, R.class);
        // log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
        // if (R.isError(result)) {
        //     return R.fail(result.getCode(), result.getMsg());
        // }
        // return R.ok(JSONUtil.toJsonStr(result.getData()));
    }


    /**
     * 根据手机号获取我的待预约个检订单
     *
     * @param request
     * @return
     */
    @PostMapping("/getPersonList")
    @ApiOperation(value = "获取我的待预约个检订单", notes = "根据手机号获取我的待预约个检订单列表")
    public R getPersonList(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        //log.info("根据手机号获取我的待预约团体订单列表,data：{}", dataStr);
        String phone = (String) dataStr;
        //log.info("请求手机号：{}", paramStr);
        if (StringUtils.isBlank(phone)) {
            return R.fail(HttpStatus.BAD_REQUEST, "手机号不能为空！");
        }
        //从体检者表里根据手机号获取待预约状态登记记录
        return R.ok(reservationOpenApiService.getPersonList(phone));

        // String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_PERSON_LIST_PATH;
        // String url = "http://localhost:8090" + Constants.RT_GROUP_ORDER_PATH;
        // Map<String, Object> paramMap = new HashMap<String, Object>() {{
        //     put("phone", paramStr);
        // }};
        // String post = HttpUtil.post(url, paramMap);
        // log.info("请求地址及参数：{}、{}、{}", url, paramMap, post);
        // R result = JSONUtil.toBean(post, R.class);
        // log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
        // if (R.isError(result)) {
        //     return R.fail(result.getCode(), result.getMsg());
        // }
        // return R.ok(JSONUtil.toJsonStr(result.getData()));
    }

    /**
     * 获取可预约时间段列表
     *
     * @param request
     * @return
     */
    @PostMapping("/getAvailableNums")
    @ApiOperation(value = "获取可预约时间段列表", notes = "获取可预约时间段列表")
    public R getAvailableNums(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
//        log.info("获取可预约时间段列表,dataStr：{}", dataStr);
        AppointmentAvailableParam param = JSONUtil.toBean(dataStr, AppointmentAvailableParam.class);
//        log.info("获取可预约时间段列表,param：{}", param);
        if (StringUtils.isBlank(param.getBranchId()) || Objects.isNull(param.getReservationDate())) {
            return R.fail(HttpStatus.BAD_REQUEST, "参数有误！");
        }
        //判断是否是同一天，是则查询当前时间到今天结束，否则查询一整天
        if (DateUtil.isSameDay(param.getReservationDate(), new Date())) {
            param.setReservationTime(DateUtil.formatTime(DateUtil.date()));
        } else {
            param.setReservationTime("00:00:00");
        }

        //从体检者表里根据手机号获取待预约状态登记记录
        return R.ok(reservationOpenApiService.getNewAvailableNums(param));

        // String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_AVAILABLE_NUMS_PATH;
        // String url = "http://localhost:8090" + Constants.RT_AVAILABLE_NUMS_PATH;
        // Map map = JSONUtil.toBean((String) dataStr, HashMap.class);
        // String post = HttpUtil.post(url, map);
        // log.info("请求地址及参数：{}、{}、{}", url, map, post);
        // R result = JSONUtil.toBean(post, R.class);
        // log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
        // if (R.isError(result)) {
        //     return R.fail(result.getCode(), result.getMsg());
        // }
        // return R.ok(JSONUtil.toJsonStr(result.getData()));
    }

    /**
     * 接收预约申请信息
     *
     * @param request
     */
    @PostMapping("/apply")
    @ApiOperation(value = "接收预约申请信息", notes = "接收预约申请信息")
    public R<String> apply(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        log.info("接收预约申请信息,data：{}", dataStr);
        AppointmentDto data = JSONUtil.toBean(dataStr, AppointmentDto.class);
        //检验参数
        if (StringUtils.isBlank(data.getBranchId()) || Objects.isNull(data.getLevelId())
                || Objects.isNull(data.getTimeId()) || Objects.isNull(data.getReservationDate())
                || StringUtils.isBlank(data.getNumorgresv()) || StringUtils.isBlank(data.getRealName())
                || StringUtils.isBlank(data.getIdcard()) || StringUtils.isBlank(data.getMobile())
                || StringUtils.isBlank(data.getComboId()) || Objects.isNull(data.getFUsecodehiden()) ||
                StringUtils.isBlank(data.getSystemId())
        ) {
            return R.fail(HttpStatus.BAD_REQUEST, "参数有误！");
        }
        if (data.getFUsecodehiden() == 1) {
            //团检
            if (StringUtils.isBlank(data.getPatientcode()) || StringUtils.isBlank(data.getIdOrg())) {
                return R.fail(HttpStatus.BAD_REQUEST, "参数有误！");
            }
        }
        String reservationNo = reservationService.check(data.getNumorgresv(), data.getPatientcode(), data.getFUsecodehiden());
        if (StringUtils.isBlank(reservationNo)) {
            //可预约
            return R.ok(reservationOpenApiService.apply(data));
        } else {
            //已预约
            return R.fail(HttpStatus.CONFLICT, reservationNo);
        }

        // String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_APPLY_PATH;
        // String url = "http://localhost:8090" + Constants.RT_APPLY_PATH;
        // Map map = JSONUtil.toBean((String) dataStr, HashMap.class);
        // String post = HttpUtil.post(url, map);
        // log.info("请求地址及参数：{}、{}、{}", url, map, post);
        // R result = JSONUtil.toBean(post, R.class);
        // log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
        // if (R.isError(result)) {
        //     return R.fail(result.getCode(), result.getMsg());
        // }
        // return R.ok(JSONUtil.toJsonStr(result.getData()));
    }

    /**
     * 检查团检订单是否可以预约
     *
     * @param request
     */
    @PostMapping("/checkGroup")
    @ApiOperation(value = "检查团检订单是否可以预约", notes = "检查团检订单是否可以预约")
    public R<String> checkGroup(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        //log.info("检查团检订单是否可以预约,data：{}", dataStr);
        AppointmentDto data = JSONUtil.toBean(dataStr, AppointmentDto.class);
        //检验参数
        if (StringUtils.isBlank(data.getBranchId()) || Objects.isNull(data.getLevelId())
                || Objects.isNull(data.getTimeId()) || Objects.isNull(data.getReservationDate())
                || StringUtils.isBlank(data.getNumorgresv()) || StringUtils.isBlank(data.getRealName())
                || StringUtils.isBlank(data.getIdcard()) || StringUtils.isBlank(data.getMobile())
                || StringUtils.isBlank(data.getComboId()) || Objects.isNull(data.getFUsecodehiden()) ||
                StringUtils.isBlank(data.getSystemId())
        ) {
            return R.fail(HttpStatus.BAD_REQUEST, "参数有误！");
        }
        if (data.getFUsecodehiden() == 1) {
            //团检
            if (StringUtils.isBlank(data.getPatientcode()) || StringUtils.isBlank(data.getIdOrg())) {
                return R.fail(HttpStatus.BAD_REQUEST, "参数有误！");
            }
        }
        String reservationNo = reservationService.check(data.getNumorgresv(), data.getPatientcode(), data.getFUsecodehiden());
        if (StringUtils.isBlank(reservationNo)) {
            //可预约
            return R.ok(reservationOpenApiService.apply(data));
        } else {
            //已预约
            return R.fail(HttpStatus.CONFLICT, reservationNo);
        }


        // String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_APPLY_PATH;
        // Map map = JSONUtil.toBean((String) dataStr, HashMap.class);
        // String post = HttpUtil.post(url, map);
        // log.info("请求地址及参数：{}、{}、{}", url, map, post);
        // R result = JSONUtil.toBean(post, R.class);
        // log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
        // if (R.isError(result)) {
        //     return R.fail(result.getCode(), result.getMsg());
        // }
        // return R.ok(JSONUtil.toJsonStr(result.getData()));
    }

    /**
     * 预约取消（预约取消）
     *
     * @param request
     */
    @PostMapping("/cancel")
    @ApiOperation(value = "预约取消", notes = "预约取消")
    public R<String> cancel(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        //log.info("预约取消,data：{}", dataStr);
        ReservationCancelParam param = JSONUtil.toBean(dataStr, ReservationCancelParam.class);
        //校验
        if (StringUtils.isNotEmpty(param.getPhone()) && StringUtils.isNotEmpty(param.getReservationNo()) ){
            long count = reservationService.count(new LambdaQueryWrapper<Reservation>()
                    .eq(Reservation::getReservationNo, param.getReservationNo())
                    .eq(Reservation::getMobile, param.getPhone())
            );
            if (count==0){
                throw new ServiceException("该预约号的信息不匹配！");
            }
        }
        if (ObjectUtils.isNotEmpty(param) && StringUtils.isNotEmpty(param.getReservationNo())){
            Reservation reservation = reservationService.getOne(new LambdaQueryWrapper<Reservation>().eq(Reservation::getReservationNo, param.getReservationNo()));
            if (ObjectUtils.isEmpty(reservation)){
                throw new ServiceException("该预约号不存在！");
            }
            if (reservation.getStatus() == 3) {
                throw new ServiceException("该预约信息无法取消！");
            }
            param.setFUsecodehiden(reservation.getFUsecodehiden());
            param.setPcodeOrOrderId(reservation.getFUsecodehiden() == 1 ? reservation.getPatientcode() :
                    "app0715".equals(param.getSystemId()) ? reservation.getPatientcode():
                    StringUtils.isNotEmpty(reservation.getBizOrderNum()) ? reservation.getBizOrderNum() : reservation.getPatientcode());
        }else {
           throw new ServiceException("数据错误！");
        }

        return reservationOpenApiService.cancel(param);
        // String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_CANCEL_PATH;
        // Map map = JSONUtil.toBean((String) dataStr, HashMap.class);
        // String post = HttpUtil.post(url, map);
        // log.info("请求地址及参数：{}、{}、{}", url, map, post);
        // R result = JSONUtil.toBean(post, R.class);
        // log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
        // if (R.isError(result)) {
        //     return R.fail(result.getCode(), result.getMsg());
        // }
        // return R.ok(JSONUtil.toJsonStr(result.getData()));
    }

    /**
     * 推送体检报告
     *
     * @param data 接收预约信息
     */
    @IpAuth
    @PostMapping("/report")
    @ApiOperation(value = "推送体检报告", notes = "推送体检报告")
    public R<String> report(ReportDto data) {
        //log.info("推送体检报告：{}", data);
        return R.ok(null);
    }


    /**
     * 根据手机号获取我的待预约团体订单列表
     *
     * @param request
     * @return
     */
    @PostMapping("/getReservationByCode")
    @ApiOperation(value = "根据体检号获取预约详情", notes = "根据体检号获取预约详情")
    public R getReservationByCode(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        String patientcode = (String) dataStr;
        log.info("根据体检号获取预约详情,data：{}", dataStr);
        if (StringUtils.isBlank(patientcode)) {
            R.fail("体检号不能为空！");
        }
        Reservation reservation = reservationOpenApiService.getOne(new LambdaQueryWrapper<Reservation>()
                .eq(Reservation::getPatientcode, patientcode)
                .in(Reservation::getStatus, 1)
        );
        if (ObjectUtils.isNotEmpty(reservation)) {
            SysBranch sysBranch = sysBranchService.getOne(new LambdaQueryWrapper<SysBranch>().eq(SysBranch::getBranchId, reservation.getBranchId()));
            reservation.setBranchName(sysBranch.getFzx());
            return R.ok(reservation);
        }
        return R.fail("体检号不存在或者已删除！");
        // String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_GETRESERVATIONBYCODE_PATH;
        // String url = "http://localhost:8090" + Constants.RT_GETRESERVATIONBYCODE_PATH;
        // Map<String, Object> paramMap = new HashMap<String, Object>() {{
        //     put("patientcode", paramStr);
        // }};
        // String post = HttpUtil.post(url, paramMap);
        // log.info("请求地址及参数：{}、{}、{}", url, paramMap, post);
        // R result = JSONUtil.toBean(post, R.class);
        // log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
        // if (R.isError(result)) {
        //     return R.fail(result.getCode(), result.getMsg());
        // }
        // return R.ok(JSONUtil.toJsonStr(result.getData()));
    }


    /**
     * 根据手机号获取我的待预约团体订单列表
     *
     * @param request
     * @return
     */
    @PostMapping("/personApply")
    @ApiOperation(value = "个检提交预约申请", notes = "个检提交预约申请")
    public R personApply(HttpServletRequest request) {
        String dataStr = (String) request.getAttribute("dataStr");
        log.info("根据体检号获取预约详情,data：{}", dataStr);
        AppointmentDto param = JSONUtil.toBean(dataStr, AppointmentDto.class);
        String reservationNo = reservationOpenApiService.personApply(param);
        return R.ok(reservationNo);
        //Map map = JSONUtil.toBean((String) dataStr, HashMap.class);
        // String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_PERSONAPPLY_PATH;
        // String url = "http://localhost:8090" + Constants.RT_PERSONAPPLY_PATH;
        // String post = HttpUtil.post(url, map);
        // log.info("请求地址及参数：{}、{}、{}", url, paramMap, post);
        // R result = JSONUtil.toBean(post, R.class);
        // log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
        // if (R.isError(result)) {
        //     return R.fail(result.getCode(), result.getMsg());
        // }
        // return R.ok(result.getData().toString());
    }

}

