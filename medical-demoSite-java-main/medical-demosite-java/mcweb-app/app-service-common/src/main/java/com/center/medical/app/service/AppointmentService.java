package com.center.medical.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.app.bean.app.dto.AppointmentDto;
import com.center.medical.app.bean.dto.AppointmentAvailableList;
import com.center.medical.app.bean.dto.CancelAppointmentDto;
import com.center.medical.app.bean.dto.GroupOrderDto;
import com.center.medical.app.bean.param.NewCancelParam;
import com.center.medical.app.bean.model.Appointment;
import com.center.medical.app.bean.model.Reservation;
import com.center.medical.app.bean.param.AppointmentAvailableParam;
import com.center.medical.app.bean.param.AppointmentParam;
import com.center.medical.app.bean.param.CheckAppointmentParam;
import com.center.medical.app.common.util.PageParam;

import java.util.List;

/**
 * 体检预约(Appointment)表服务接口
 *
 * @author 路飞船长
 * @since 2023-03-27 18:53:56
 */
public interface AppointmentService extends IService<Appointment> {

    /**
     * 分页查询[体检预约]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Appointment> getPage(PageParam<Appointment> page, AppointmentParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Appointment getInfoById(String id);

    /**
     * 获取可预约时间段列表
     *
     * @param param
     * @return
     */
    List<AppointmentAvailableList> getAvailableNums(AppointmentAvailableParam param);

    /**
     * 根据手机号获取我的待预约团体订单列表
     *
     * @param phone
     * @return
     */
    List<GroupOrderDto> getGroupOrderList(String phone);

    /**
     * 检查是否可以预约
     *
     * @param param 检查参数
     * @return
     */
    Boolean check(CheckAppointmentParam param);

    /**
     * 提交预约申请
     *
     * @param data
     * @return
     */
    String apply(AppointmentDto data);

    /**
     * 取消预约操作
     *
     * @param param  取消预约参数
     * @param userId 当前用户ID
     * @return
     */
    Boolean cancel(CancelAppointmentDto param, String userId);

    /**
     * 获取个检订单
     * @param phone
     * @return
     */
    List<GroupOrderDto> getPersonList(String phone);

    /**
     * 根据体检号获取预约详情
     * @param patientcode
     * @return
     */
    Reservation getReservationByCode(String patientcode);

    /**
     * 个检提交预约申请
     * @param param
     * @return
     */
    String personApply(AppointmentDto param);

    /**
     * 立即预约
     * @param data
     * @return
     */
    String appointmentNow(AppointmentDto data);

    /**
     * 新版取消预约
     * @param param
     * @return
     */
    boolean newCancel(NewCancelParam param);
}

