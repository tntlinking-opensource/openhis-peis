package com.center.medical.reception.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.dto.*;
import com.center.medical.reception.bean.param.MyOrderParam;
import com.center.medical.reception.bean.param.UnitReservationParam;
import com.center.medical.reservation.bean.dto.AppointmentDto;

import java.util.List;

/**
 * QT体检者表(Peispatient)服务接口
 *
 * @author ay
 * @since 2024-03-13 09:54:40
 */
public interface NewReservationService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Peispatient> getPage(PageParam<Peispatient> page, Peispatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 首页预约列表
     * @param phone
     * @return
     */
    List<HomePageListDto> getHomePageList(String phone);

    /**
     * 获取用户档案信息
     * @param phone
     * @return
     */
    List<UserIdcardDto> getUserIdcard(String phone);

    /**
     * 获取体检者数据
     * @param patientcode
     * @return
     */
    GetNewReDataDto getNewReData(String patientcode);

    /**
     * 单位预约查询单位
     * @param name
     * @return
     */
    IPage<QueryUnitDto> queryUnit(PageParam<QueryUnitDto> page,String name);

    /**
     * 单位预约提交
     * @param param
     * @return
     */
    String unitReservation(UnitReservationParam param);

    /**
     * 帮人预约
     * @param phone
     * @return
     */
    List<HelpAppointDto> helpAppoint(String phone);

    /**
     * 我的订单
     * @param param
     * @return
     */
    IPage<MyOrderDto> myOrder(PageParam<MyOrderDto> page, MyOrderParam param);

    /**
     * 订单角标
     * @param phone
     * @return
     */
    OrderMarkersDto orderMarkers(String phone);

    /**
     * 立即预约
     * @param data
     * @return
     */
    String appointmentNow(AppointmentDto data);

    /**
     * 检查体检号和手机号是否相符合
     * @param patientcode
     * @param phone
     * @return
     */
    Boolean checkPatientcode(String patientcode, String phone);
}

