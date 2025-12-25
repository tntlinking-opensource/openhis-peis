package com.center.medical.reservation.open;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.dto.BusinessSourceSetting;
import com.center.medical.bean.dto.GroupOrderDto;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.annotation.IpAuth;
import com.center.medical.common.constant.HttpStatus;
import com.center.medical.common.constant.SourcesConstants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.reservation.bean.dto.AppointmentAvailableList;
import com.center.medical.reservation.bean.dto.AppointmentDto;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.param.AppointmentAvailableParam;
import com.center.medical.reservation.bean.param.ReservationCancelParam;
import com.center.medical.reservation.service.ReservationOpenApiService;
import com.center.medical.reservation.service.ReservationService;
import com.center.medical.system.bean.model.BusinessSource;
import com.center.medical.system.service.BusinessSourceService;
import com.center.medical.system.service.ISysBranchService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 体检预约外部接口（提供给预约小程序、平安好医生、康淘等平台）
 *
 * @author ay
 * @since 2023-03-18 08:58:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 1)
@Api(tags = "体检预约外部接口")
@RequestMapping("/lan/reservation/pf")
public class ReservationOpenApiController extends BaseController {
    /**
     * 服务对象
     */
    private final ReservationOpenApiService reservationOpenApiService;
    private final ReservationService reservationService;
    private final BusinessSourceService businessSourceService;
    private final ISysBranchService sysBranchService;

    /**
     * 根据手机号获取我的待预约团体订单列表
     *
     * @param phone 手机号
     * @return
     */
    @IpAuth
    @PostMapping("/getGroupOrderList")
    @ApiOperation(value = "获取我的待预约团体订单", notes = "根据手机号获取我的待预约团体订单列表")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R<List<GroupOrderDto>> getGroupOrderList(String phone) {
        log.info("根据手机号获取我的待预约团体订单列表,phone：{}", phone);
        if (StringUtils.isBlank(phone)) {
            return R.fail(HttpStatus.BAD_REQUEST, "手机号不能为空！");
        }
        //从体检者表里根据手机号获取待预约状态登记记录
        return R.ok(reservationOpenApiService.getGroupOrderList(phone));
    }


    /**
     * 根据手机号获取我的待预约个检订单
     *
     * @param phone 手机号
     * @return
     */
    @IpAuth
    @PostMapping("/getPersonList")
    @ApiOperation(value = "获取我的待预约个检订单", notes = "根据手机号获取我的待预约团体订单列表")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R<List<GroupOrderDto>> getPersonList(String phone) {
        log.info("根据手机号获取个检预约订单列表,phone：{}", phone);
        if (StringUtils.isBlank(phone)) {
            return R.fail(HttpStatus.BAD_REQUEST, "手机号不能为空！");
        }
        //从体检者表里根据手机号获取待预约状态登记记录
        return R.ok(reservationOpenApiService.getPersonList(phone));
    }

    /**
     * 获取可预约时间段列表
     *
     * @param param 筛选参数
     * @return
     */
    @IpAuth
    @PostMapping("/getAvailableNums")
    @ApiOperation(value = "获取可预约时间段列表", notes = "获取可预约时间段列表")
    public R<List<AppointmentAvailableList>> getAvailableNums(AppointmentAvailableParam param) {

        log.info("获取可预约时间段列表1,param：{}", param);
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
        return R.ok(reservationOpenApiService.getAvailableNums(param));
    }


    /**
     * 预约申请信息
     *
     * @param data
     * @return
     */
    @IpAuth
    @PostMapping("/apply")
    @ApiOperation(value = "提交预约申请", notes = "提交预约申请信息")
    public R<String> apply(AppointmentDto data) {
        log.info("用户提交预约申请，data={}", JSONUtil.toJsonStr(data));
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
    }


    /**
     * 预约取消
     *
     * @param param 预约取消参数
     * @return
     */
    @IpAuth
    @PostMapping("/cancel")
    @ApiOperation(value = "预约取消", notes = "预约取消")
    public R<String> cancel(ReservationCancelParam param) {
        log.info("预约取消，param={}", param);
        return reservationOpenApiService.cancel(param);
    }


    @PostMapping("/test")
    @ApiOperation(value = "测试", notes = "测试")
    public R insert(@RequestParam("token") String token) {
        log.info("token{}", token);
        BusinessSource source = businessSourceService.getOne(new LambdaQueryWrapper<BusinessSource>()
                .eq(BusinessSource::getSourceId, SourcesConstants.SOURCE_ID_APP)
                .eq(BusinessSource::getStatus, 1));
        log.info("source:{}", source.getSetting());
        log.info("source:{}", JSONUtil.toBean(source.getSetting(), BusinessSourceSetting.class));
        return R.ok(RedisUtil.get(token));
    }


    /**
     * 根据手机号获取我的待预约团体订单列表
     *
     * @param patientcode 手机号
     * @return
     */
    @IpAuth
    @PostMapping("/getReservationByCode")
    @ApiOperation(value = "根据体检号获取预约详情", notes = "根据体检号获取预约详情")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<Reservation> getReservationByCode(String patientcode) {
        Reservation reservation = reservationOpenApiService.getOne(new LambdaQueryWrapper<Reservation>()
                .eq(Reservation::getPatientcode, patientcode)
                .in(Reservation::getStatus, 1)
        );
        if (ObjectUtils.isNotEmpty(reservation)) {
            SysBranch sysBranch = sysBranchService.getOne(new LambdaQueryWrapper<SysBranch>().eq(SysBranch::getBranchId, reservation.getBranchId()));
            reservation.setBranchName(sysBranch.getFzx());
        }
        return R.ok(reservation);
    }


    /**
     * 个检提交预约申请
     *
     * @param param
     * @return
     */
    @IpAuth
    @PostMapping("/personApply")
    @ApiOperation(value = "个检提交预约申请", notes = "个检提交预约申请")
    public R<String> personApply(AppointmentDto param) {
        String reservationNo = reservationOpenApiService.personApply(param);
        return R.ok(reservationNo);
    }
}

