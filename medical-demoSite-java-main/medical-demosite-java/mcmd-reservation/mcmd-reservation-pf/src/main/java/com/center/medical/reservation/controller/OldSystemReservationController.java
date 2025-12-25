package com.center.medical.reservation.controller;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.model.ReservationSetting;
import com.center.medical.reservation.bean.param.GetReservationParam;
import com.center.medical.reservation.bean.param.OldSystemReParam;
import com.center.medical.reservation.bean.param.ReservationCancelParam;
import com.center.medical.reservation.bean.param.ReservationSettingParam;
import com.center.medical.reservation.service.ReservationOpenApiService;
import com.center.medical.reservation.service.ReservationService;
import com.center.medical.reservation.service.ReservationSettingService;
import com.center.medical.service.PeispatientChargeMainService;
import com.center.medical.system.service.ISysConfigService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 老系统预约控制层
 *  老系统不用了，全部都用新系统
 * @author ay
 * @since 2023-12-02 08:58:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 10)
@Api(tags = "老系统预约")
@RequestMapping("open/api/oldSystem/reservation")
public class OldSystemReservationController extends BaseController {

    private final ReservationService reservationService;
    private final ReservationSettingService reservationSettingService;
    private final MapperFacade mapperFacade;
    private final ISysConfigService iSysConfigService;
    private final PeispatientChargeMainService peispatientChargeMainService;
    private final ReservationOpenApiService reservationOpenApiService;

    /**
     * 新增预约
     *
     * @param oldSystemReParam 实体对象
     * @return 新增结果
     */
    @PostMapping(value = "/saOrUp",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "新增或修改预约", notes = "新增或修改预约")
    @RepeatSubmit(interval = 3000, message = "正在处理，请稍等")
    public R<String> insert(@RequestBody OldSystemReParam oldSystemReParam) {
        log.info("登记完成预约,请求的参数是:{}", oldSystemReParam);
        //去空格
        if (StringUtils.isNotEmpty(oldSystemReParam.getIdcard())){
            oldSystemReParam.setIdcard(oldSystemReParam.getIdcard().trim());
        }
        Reservation reservation = mapperFacade.map(oldSystemReParam, Reservation.class);
        reservation.setSystemId("0");
//        reservation.setOperator(SecurityUtils.getUserNo());
//        //设置进去应付金额，用于审批流
//        PeispatientChargeMain peispatientChargeMain = peispatientChargeMainService.getOne(new LambdaQueryWrapper<PeispatientChargeMain>()
//                .eq(PeispatientChargeMain::getPatientcode, reservation.getPatientcode()));
//        reservation.setMoneyamount(peispatientChargeMain.getMoneyamount());
        //共用一个池子线上,线下请求线上
        boolean online = ZhongkangConfig.isOnline();
        if (online) {
            String systemId = reservation.getSystemId();
            if (StringUtils.isBlank(systemId)) {
                //体检系统内部预约

            } else {
                //外部系统预约

            }
            return this.reservationService.saOrUpReservation(reservation);
        } else {
            Domain domain = iSysConfigService.getDomain();
            String pfDomain = domain.getPlatformDomain();
//            String pfDomain = "http://localhost:8090";
            String url = pfDomain + Constants.ONLINE_OLDSYSTEM_SAORUP;
            String result = HttpUtils.sendPost(url, JSONUtil.toJsonStr(reservation));
            R r = JSONUtil.toBean(result,R.class);
            if(R.SUCCESS!=r.getCode()){
                throw new ServiceException(r.getMsg());
            }
            return R.ok(r.getData().toString());
        }

    }





    /**
     * 获取预约时段列表
     *
     * @param param 请求参数
     * @return 所有数据
     */
    @PostMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取预约时段列表", notes = "获取预约时段列表")
    public R<List<ReservationSetting>> getList(@Validated @RequestBody ReservationSettingParam param) {
        if (StringUtils.isBlank(param.getBranchId())) {
            param.setBranchId(SecurityUtils.getCId());
        }
        //共用一个池子线上,线下请求线上
        boolean online = ZhongkangConfig.isOnline();
        if (online) {
            return R.ok(this.reservationSettingService.getList(param));
        } else {
            Domain domain = iSysConfigService.getDomain();
            String pfDomain = domain.getPlatformDomain();
            String url = pfDomain + Constants.ONLINE_OLDSYSTEM_LIST;
            log.info("请求线上预约,请求的参数是:{}", param);
            String result = HttpUtils.sendPost(url, JSONUtil.toJsonStr(param));
            log.info("请求线上预约,请求的数据是:{}", result);
            R r = JSONUtil.toBean(result,R.class);
            if(R.SUCCESS!=r.getCode()){
                throw new ServiceException(r.getMsg());
            }
            if (ObjectUtils.isEmpty(r.getData()) || "null".equals(r.getData().toString())){
                return R.ok(null);
            }
            return R.ok(JSONUtil.toList(JSONUtil.toJsonStr(r.getData()), ReservationSetting.class));
        }

    }



    /**
     * 根据预约号或体检号获取预约详情
     *
     * @param param 请求参数
     * @return 所有数据
     */
    @GetMapping("/getReservation")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取预约详情", notes = "根据预约号或体检号获取预约详情")
    public R<Reservation> getReservation(GetReservationParam param) {
        //共用一个池子线上,线下请求线上
        boolean online = ZhongkangConfig.isOnline();
        if (online) {
            return R.ok(this.reservationService.getReservation(param));
        }else {
            Domain domain = iSysConfigService.getDomain();
            String pfDomain = domain.getPlatformDomain();
//            String pfDomain = "http://localhost:8090";
            String url = pfDomain + Constants.ONLINE_OLDSYSTEM_GETRESERVATION;

            String paramStr = "";
            if (StringUtils.isNotEmpty(param.getReservationNo())){
                paramStr = "reservationNo=" + param.getReservationNo();
            }
            if (StringUtils.isNotEmpty(param.getPatientcode())){
                paramStr = "patientcode=" + param.getPatientcode();
            }
            if (StringUtils.isNotEmpty(param.getMobile())){
                paramStr = "mobile=" + param.getMobile();
            }
            log.info("请求线上预约,请求的参数是:{}", paramStr);
            String result = HttpUtils.sendGet(url, paramStr);
            log.info("请求线上预约,返回数据是:{}", result);
            R r = JSONUtil.toBean(result,R.class);
            if(R.SUCCESS!=r.getCode()){
                throw new ServiceException(r.getMsg());
            }
            if (ObjectUtils.isEmpty(r.getData()) || "null".equals(r.getData().toString())){
                return R.ok(null);
            }
            return R.ok(JSONUtil.toBean(r.getData().toString(), Reservation.class,false));
        }

    }




    /**
     * 预约取消
     *
     * @param param
     */
    @PostMapping("/cancel")
    @ApiOperation(value = "预约取消", notes = "预约取消")
    public R<String> cancel(@RequestBody ReservationCancelParam param) {
        log.info("预约取消,请求的参数是:{}", param);
        boolean online = ZhongkangConfig.isOnline();
        if (online) {
            if (ObjectUtils.isNotEmpty(param) && StringUtils.isNotEmpty(param.getReservationNo())) {
                Reservation reservation = reservationService.getOne(new LambdaQueryWrapper<Reservation>().eq(Reservation::getReservationNo, param.getReservationNo()));
                if (ObjectUtils.isEmpty(reservation)) {
                    throw new ServiceException("该预约号不存在！");
                }
                if (reservation.getStatus() == 3) {
                    throw new ServiceException("该预约信息无法取消！");
                }
                param.setSystemId(reservation.getSystemId());
                param.setFUsecodehiden(reservation.getFUsecodehiden());
                param.setPcodeOrOrderId(reservation.getFUsecodehiden() == 1 ? reservation.getPatientcode() :
                        "0".equals(param.getSystemId()) ? reservation.getPatientcode() :
                                StringUtils.isNotEmpty(reservation.getBizOrderNum()) ? reservation.getBizOrderNum() : reservation.getPatientcode());
            } else {
                throw new ServiceException("数据错误！");
            }
            return reservationOpenApiService.cancel(param);
        }else {
            Domain domain = iSysConfigService.getDomain();
            String pfDomain = domain.getPlatformDomain();
            String url = pfDomain + Constants.ONLINE_OLDSYSTEM_CANCEL;
            String result = HttpUtils.sendPost(url, JSONUtil.toJsonStr(param));
            log.info("请求线上取消预约,请求的数据是:{}", result);
            R r = JSONUtil.toBean(result,R.class);
            if(R.SUCCESS!=r.getCode()){
                throw new ServiceException(r.getMsg());
            }
            return R.ok(r.getData().toString());
        }
    }
}
