package com.center.medical.app.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.app.dto.AppointmentDto;
import com.center.medical.app.bean.dto.AppointmentAvailableList;
import com.center.medical.app.bean.dto.CancelAppointmentDto;
import com.center.medical.app.bean.dto.GroupOrderDto;
import com.center.medical.app.bean.model.Appointment;
import com.center.medical.app.bean.model.Reservation;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.bean.param.*;
import com.center.medical.app.common.constants.Constant;
import com.center.medical.app.common.enums.AppHttpStatus;
import com.center.medical.app.common.enums.AppointmentStatus;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.response.AppResponse;
import com.center.medical.app.common.util.PageParam;
import com.center.medical.app.config.OsZhongKangConfig;
import com.center.medical.app.config.ShopConfig;
import com.center.medical.app.dao.AppointmentMapper;
import com.center.medical.app.service.AppointmentService;
import com.center.medical.app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 体检预约(Appointment)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-03-27 18:53:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    private final AppointmentMapper appointmentMapper;
    private final ShopConfig shopConfig;
    private final MapperFacade mapperFacade;
    private final Snowflake snowflake;
    private final OsZhongKangConfig osZhongKangConfig;
    private final UserService userService;

    /**
     * 分页查询[体检预约]列表
     *
     * @param page  分页参数
     * @param param Appointment查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Appointment> getPage(PageParam<Appointment> page, AppointmentParam param) {
        return appointmentMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Appointment getInfoById(String id) {
        return appointmentMapper.getInfoById(id);
    }

    /**
     * 获取可预约时间段列表
     *
     * @param param
     * @return
     */
    @Override
    public List<AppointmentAvailableList> getAvailableNums(AppointmentAvailableParam param) {

        log.info("获取可预约时间段列表:{}", param);
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSON.toJSONString(param));

        if (Objects.nonNull(mapParam)) {
            AppResponse response = null;
            try {
                //发送请求
                response = osZhongKangConfig.sendWithMap(Constant.RT_AVAILABLE_NUMS_PATH, 2, mapParam);
            } catch (Exception e) {
                //请求失败
                log.error("{}请求连接失败：{}", Constant.RT_AVAILABLE_NUMS_PATH, e);
                throw new AppBindException("连接异常！");
            }
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    return JSONUtil.toList(JSONUtil.toJsonStr(response.getData()), AppointmentAvailableList.class);
                } else {
                    throw new AppBindException(AppHttpStatus.SUCCESS, "暂无可预约的时间段！");
                }

            } else {
                //获取失败
                throw new AppBindException(Objects.nonNull(response.getData()) ? JSONUtil.toJsonStr(response.getData()) : "获取失败，请稍后再重试！");
            }
        } else {
            throw new AppBindException("获取失败，请稍后再重试！");
        }

    }

    /**
     * 根据手机号获取我的待预约团体订单列表
     *
     * @param phone
     * @return
     */
    @Override
    public List<GroupOrderDto> getGroupOrderList(String phone) {
        log.info("根据手机号获取我的待预约团体订单列表:{}", phone);
        //加密
        Map<String, Object> param = osZhongKangConfig.encryptV2(phone);
        if (Objects.nonNull(param)) {
            AppResponse response = null;
            try {
                //发送请求
                response = osZhongKangConfig.sendWithMap(Constant.RT_GROUP_ORDER_LIST_PATH, 2, param);
            } catch (Exception e) {
                //请求失败
                log.error("{}请求连接失败：{}", Constant.RT_GROUP_ORDER_LIST_PATH, e);
                throw new AppBindException("连接异常！");
            }
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    return JSONUtil.toList(JSONUtil.toJsonStr(response.getData()), GroupOrderDto.class);
                } else {
                    throw new AppBindException(AppHttpStatus.SUCCESS, "暂无可预约的团体订单！");
                }
            } else {
                throw new AppBindException("获取失败，请稍后再重试！");
            }
        } else {
            throw new AppBindException("获取失败，请稍后再重试！");
        }
    }

    /**
     * 检查团检订单是否可以预约
     *
     * @param param 检查参数
     * @return
     */
    @Override
    public Boolean check(CheckAppointmentParam param) {
        Long count = 0L;
        //根据体检号查询是否已有预约
        if (param.getFUsecodehiden() == 1) {
            //团检
            count = appointmentMapper.selectCount(new LambdaQueryWrapper<Appointment>()
                    .eq(Appointment::getPatientcode, param.getPatientcode())
                    .ge(Appointment::getStatus, AppointmentStatus.PENDING.value())
                    .lt(Appointment::getStatus, AppointmentStatus.FINISHED.value())
                    .eq(Appointment::getIsDelete, 0));
        } else {
            LambdaQueryWrapper<Appointment> lambdaQueryWrapper = new LambdaQueryWrapper<Appointment>()
                    .ge(Appointment::getStatus, AppointmentStatus.PENDING.value())
                    .lt(Appointment::getStatus, AppointmentStatus.FINISHED.value())
                    .eq(Appointment::getIsDelete, 0);
            //系统里面的个检没有订单号，所以没有订单号的就用体检号查
            if (StringUtils.isNotEmpty(param.getNumorgresv())) {
                lambdaQueryWrapper.eq(Appointment::getNumorgresv, param.getNumorgresv());
            } else {
                lambdaQueryWrapper.eq(Appointment::getPatientcode, param.getPatientcode());
            }
            count = appointmentMapper.selectCount(lambdaQueryWrapper);
        }
        return count > 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    /**
     * 提交预约申请
     *
     * @param data
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String apply(AppointmentDto data) {
        Appointment appointment = mapperFacade.map(data, Appointment.class);
        appointment.setStatus(AppointmentStatus.PENDING.value());
        //向预约系统发送预约请求
        data.setSystemId(shopConfig.getZkSystemFlag());
        if (StringUtils.isBlank(data.getId())) {
            String id = snowflake.nextIdStr();
            appointment.setId(id);
            data.setId(id);
        }

        log.info("根据手机号获取我的待预约团体订单列表:{}", JSON.toJSONStringWithDateFormat(data, "yyyy-MM-dd HH:mm:ss"));
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSON.toJSONStringWithDateFormat(data, "yyyy-MM-dd HH:mm:ss"));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = null;
            try {
                //发送请求
                response = osZhongKangConfig.sendWithMap(Constant.RT_APPLY_PATH, 2, mapParam);
            } catch (Exception e) {
                //请求失败
                log.error("{}请求连接失败：{}", Constant.RT_APPLY_PATH, e);
                throw new AppBindException("连接异常！");
            }
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    //预约提交成功，插入预约记录
                    appointment.setStatus(AppointmentStatus.SUCCESS.value());
                    appointment.setReservationNo((String) response.getData());
                    appointment.setCreatedate(new Date());
                    appointmentMapper.insert(appointment);
                    return (String) response.getData();
                } else if (response.getCode() == AppResponse.CONFLICT) {
                    //体检系统已存在预约
                    appointment.setStatus(AppointmentStatus.SUCCESS.value());
                    appointment.setReservationNo(response.getMsg());
                    appointment.setCreatedate(new Date());
                    appointmentMapper.insert(appointment);
                    return response.getMsg();
                } else {
                    //预约提交失败
                    log.error("向体检系统提交预约失败：{}", response.getData());
                }
            } else {
                log.error("向体检系统提交预约请求失败！");
            }
        } else {
            log.error("加密失败导致提交预约失败！");
        }
        throw new AppBindException("预约失败，请稍后再重试！");

    }

    /**
     * 取消预约操作
     *
     * @param param  取消预约参数
     * @param userId 当前用户ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancel(CancelAppointmentDto param, String userId) {
        Appointment appointment = appointmentMapper.selectOne(new LambdaQueryWrapper<Appointment>()
                .eq(Appointment::getId, param.getId()).eq(Appointment::getUserId, userId));
        if (Objects.isNull(appointment)) {
            throw new AppBindException("找不到预约信息");
        }
        if (appointment.getStatus() < AppointmentStatus.SUCCESS.value() || appointment.getStatus() > AppointmentStatus.FINISHED.value()) {
            throw new AppBindException("该预约已失效，无需再取消！");
        }
        if (appointment.getStatus() < 1 || appointment.getStatus() > 5) {
            throw new AppBindException("该预约信息无法取消！");
        }
        appointment.setStatus(AppointmentStatus.CANCELING.value());
        appointment.setFailReasion(param.getReason());
        appointment.setModifydate(new Date());
        appointmentMapper.updateById(appointment);

        ReservationCancelParam reservationCancelParam = new ReservationCancelParam(appointment.getReservationNo(), appointment.getFUsecodehiden(),
                appointment.getFUsecodehiden() == 1 ? appointment.getPatientcode() :
                        StringUtils.isNotEmpty(appointment.getNumorgresv()) ? appointment.getNumorgresv() : appointment.getPatientcode(),
                param.getReason(), shopConfig.getZkSystemFlag());

        //向预约系统发送取消预约请求
        log.info("根据手机号获取我的待预约团体订单列表:{}", JSONUtil.toJsonStr(reservationCancelParam));
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(reservationCancelParam));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = null;
            try {
                //发送请求
                response = osZhongKangConfig.sendWithMap(Constant.RT_CANCEL_PATH, 2, mapParam);
            } catch (Exception e) {
                //请求失败
                log.error("{}请求连接失败：{}", Constant.RT_CANCEL_PATH, e);
                throw new AppBindException("连接异常！");
            }
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    //取消预约操作成功，更新预约记录
                    appointment.setStatus(AppointmentStatus.CANCELED.value());
                    appointment.setFailReasion(param.getReason());
                    appointment.setModifydate(new Date());
                    appointmentMapper.updateById(appointment);
                    return Boolean.TRUE;
                } else {
                    log.error("向体检系统提交取消预约失败：", response.getData());
                }
            } else {
                log.error("向体检系统提交取消预约请求失败：", response.getData());
            }
        } else {
            log.error("加密失败，提交取消预约请求失败：");
        }
        throw new AppBindException("取消预约操作失败，请稍后再重试！");
    }


    /**
     * 获取个检订单
     *
     * @param phone
     * @return
     */
    @Override
    public List<GroupOrderDto> getPersonList(String phone) {
        log.info("根据手机号获取我的待预约团体订单列表:{}", phone);
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(phone);
        if (Objects.nonNull(mapParam)) {
            AppResponse response = null;
            try {
                //发送请求
                response = osZhongKangConfig.sendWithMap(Constant.RT_PERSON_LIST_PATH, 2, mapParam);
            } catch (Exception e) {
                //请求失败
                log.error("{}请求连接失败：{}", Constant.RT_PERSON_LIST_PATH, e);
                throw new AppBindException("连接异常！");
            }
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    return JSONUtil.toList(JSONUtil.toJsonStr(response.getData()), GroupOrderDto.class);
                } else {
                    throw new AppBindException(AppHttpStatus.SUCCESS, "暂无可预约的团体订单！");
                }
            } else {
                log.info("获取失败：{}", response);
                throw new AppBindException(response.getMsg());
            }
        } else {
            throw new AppBindException("获取失败，请稍后再重试！");
        }
    }


    /**
     * 根据体检号获取预约详情
     *
     * @param patientcode
     * @return
     */
    @Override
    public Reservation getReservationByCode(String patientcode) {
        log.info("根据体检号获取预约详情:{}", patientcode);
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(patientcode);
        if (Objects.nonNull(mapParam)) {
            AppResponse response = null;
            try {
                //发送请求
                response = osZhongKangConfig.sendWithMap(Constant.RT_GETRESERVATIONBYCODE_PATH, 2, mapParam);
            } catch (Exception e) {
                //请求失败
                log.error("{}请求连接失败：{}", Constant.RT_GETRESERVATIONBYCODE_PATH, e);
                throw new AppBindException("连接异常！");
            }
            if (AppResponse.isSuccess(response)) {
                //获取成功
                log.info("根据体检号获取预约详情返回数据:{}", response.getData());
                return JSONUtil.toBean(JSONUtil.toJsonStr(response.getData()), Reservation.class);
            } else {
                log.info("获取失败：{}", response);
                throw new AppBindException(response.getMsg());
            }

        } else {
            log.warn("加密失败!");
            throw new AppBindException("参数不合法！");
        }
    }

    /**
     * 个检提交预约申请
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String personApply(AppointmentDto param) {
        log.info("个检提交预约申请:{}", param);
        Appointment appointment = mapperFacade.map(param, Appointment.class);
        param.setSystemId(shopConfig.getZkSystemFlag());
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSON.toJSONStringWithDateFormat(param, "yyyy-MM-dd HH:mm:ss"));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = null;
            try {
                //发送请求
                response = osZhongKangConfig.sendWithMap(Constant.RT_PERSONAPPLY_PATH, 2, mapParam);
            } catch (Exception e) {
                //请求失败
                log.error("{}请求连接失败：{}", Constant.RT_PERSONAPPLY_PATH, e);
                throw new AppBindException("连接异常！");
            }
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    //插入小程序预约
                    String id = snowflake.nextIdStr();
                    appointment.setId(id);
                    appointment.setStatus(AppointmentStatus.SUCCESS.value());
                    appointment.setCreatedate(new Date());
                    appointment.setReservationNo(response.getData().toString());
                    appointmentMapper.insert(appointment);
                    return response.getData().toString();
                }
            } else {
                log.error("向体检系统提交预约失败：{}", response.getData());
                throw new AppBindException(response.getData().toString());
            }
        } else {
            log.error("加密失败导致提交预约失败！");
        }
        throw new AppBindException("预约失败，请稍后再重试！");
    }

    /**
     * 立即预约
     * @param param
     * @return
     */
    @Override
    public String appointmentNow(AppointmentDto param) {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, param.getUserId()));
        param.setMobile(user.getUserMobile());
        log.info("个检提交预约申请:{}", param);
        param.setSystemId(shopConfig.getZkSystemFlag());
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSON.toJSONStringWithDateFormat(param, "yyyy-MM-dd HH:mm:ss"));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = null;
            try {
                //发送请求
                response = osZhongKangConfig.sendWithMap(Constant.RT_NEWRESERVATION_APPOINTMENTNOW_PATH, 2, mapParam);
            } catch (Exception e) {
                //请求失败
                log.error("{}请求连接失败：{}", Constant.RT_NEWRESERVATION_APPOINTMENTNOW_PATH, e);
                throw new AppBindException("连接异常！");
            }
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    return response.getData().toString();
                }
            } else {
                log.error("向体检系统提交预约失败：{}", response.getData());
                throw new AppBindException(response.getData().toString());
            }
        } else {
            log.error("加密失败导致提交预约失败！");
        }
        throw new AppBindException("预约失败，请稍后再重试！");
    }

    /**
     * 新版取消预约
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean newCancel(NewCancelParam param) {
//        Appointment appointment = appointmentMapper.selectOne(new LambdaQueryWrapper<Appointment>()
//                .eq(Appointment::getReservationNo, param.getReservationNo()));
//        if (Objects.isNull(appointment)) {
//            throw new AppBindException("找不到预约信息");
//        }
//        if (appointment.getStatus() < AppointmentStatus.SUCCESS.value() || appointment.getStatus() > AppointmentStatus.FINISHED.value()) {
//            throw new AppBindException("该预约已失效，无需再取消！");
//        }
//        if (appointment.getStatus() < 1 || appointment.getStatus() > 5) {
//            throw new AppBindException("该预约信息无法取消！");
//        }
//        appointment.setStatus(AppointmentStatus.CANCELING.value());
////        appointment.setFailReasion(param.getReason());
//        appointment.setModifydate(new Date());
//        appointmentMapper.updateById(appointment);
//
//        ReservationCancelParam reservationCancelParam = new ReservationCancelParam(appointment.getReservationNo(), appointment.getFUsecodehiden(),
//                appointment.getFUsecodehiden() == 1 ? appointment.getPatientcode() :
//                        StringUtils.isNotEmpty(appointment.getNumorgresv()) ? appointment.getNumorgresv() : appointment.getPatientcode(),
//                "", shopConfig.getZkSystemFlag());

        ReservationCancelParam reservationCancelParam = new ReservationCancelParam();
        reservationCancelParam.setReservationNo(param.getReservationNo());
        reservationCancelParam.setSystemId(shopConfig.getZkSystemFlag());
        reservationCancelParam.setPhone(param.getPhone());
        //向预约系统发送取消预约请求
        log.info("根据手机号获取我的待预约团体订单列表:{}", JSONUtil.toJsonStr(reservationCancelParam));
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(reservationCancelParam));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = null;
            try {
                //发送请求
                response = osZhongKangConfig.sendWithMap(Constant.RT_CANCEL_PATH, 2, mapParam);
            } catch (Exception e) {
                //请求失败
                log.error("{}请求连接失败：{}", Constant.RT_CANCEL_PATH, e);
                throw new AppBindException("取消预约失败！");
            }
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    return Boolean.TRUE;
                } else {
                    log.error("向体检系统提交取消预约失败：", response.getData());
                }
            } else {
                log.error("向体检系统提交取消预约请求失败：", response.getData());
            }
        } else {
            log.error("加密失败，提交取消预约请求失败：");
        }
        throw new AppBindException("取消预约操作失败，请稍后再重试！");
    }
}

