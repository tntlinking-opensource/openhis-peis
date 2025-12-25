package com.center.medical.reservation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.dto.GroupOrderDto;
import com.center.medical.common.core.domain.R;
import com.center.medical.reservation.bean.dto.AppointmentAvailableList;
import com.center.medical.reservation.bean.dto.AppointmentDto;
import com.center.medical.reservation.bean.dto.ReservationTimeDto;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.param.AppointmentAvailableParam;
import com.center.medical.reservation.bean.param.ReservationCancelParam;
import com.center.medical.reservation.bean.param.ReservationSettingParam;
import com.center.medical.reservation.bean.vo.ReservationDateVo;

import java.util.List;

/**
 * 体检预约外部服务接口
 *
 * @author ay
 * @since 2023-03-18 08:54:14
 */
public interface ReservationOpenApiService extends IService<Reservation> {

    /**
     * 根据手机号获取我的待预约团体订单列表
     *
     * @param phone
     * @return
     */
    List<GroupOrderDto> getGroupOrderList(String phone);

    /**
     * 获取可预约时间段列表
     *
     * @param param 筛选参数
     * @return
     */
    List<AppointmentAvailableList> getAvailableNums(AppointmentAvailableParam param);

    /**
     * 提交预约申请
     *
     * @param data
     * @return 返回值为预约号
     */
    String apply(AppointmentDto data);

    /**
     * 预约取消
     *
     * @param param 预约取消参数
     * @return
     */
    R<String> cancel(ReservationCancelParam param);

    /**
     * 获取我的待预约个检订单
     * @param phone
     * @return
     */
    List<GroupOrderDto> getPersonList(String phone);

    /**
     * 个检提交预约申请
     * @param param
     * @return
     */
    String personApply(AppointmentDto param);

    /**
     * 获取可预约时间段列表 会员类型不向下兼容
     *
     * @param param 筛选参数
     * @return
     */
    List<AppointmentAvailableList> getNewAvailableNums(AppointmentAvailableParam param);

    /**
     * 获取预约日期列表
     *
     * @param param
     * @return
     */
    List<ReservationDateVo> getReservationDateList(ReservationSettingParam param);

    /**
     * 获取预约时间段列表
     * @param param
     * @return
     */
    List<ReservationTimeDto> getReservationTimeList(AppointmentAvailableParam param);
}

