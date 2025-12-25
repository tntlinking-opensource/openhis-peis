package com.center.medical.reservation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.reservation.bean.dto.IntegratedReservationAvailableDto;
import com.center.medical.reservation.bean.dto.IntegratedReservationBranchListDto;
import com.center.medical.reservation.bean.dto.IntegratedReservationStatusDto;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.sellcrm.bean.model.Createmeal;

import java.util.List;

/**
 * 新老系统集成预约
 * @author xhp
 * @since 2024-01-03 8:03
 */
public interface IntegratedReservationService extends IService<Reservation> {
    /**
     * 根据第三方套餐id获取可预约机构门店列表
     * @param data
     * @return
     */
    List<IntegratedReservationBranchListDto> getBranchList(String data);

    /**
     * 获取可预约时间段列表
     * @param data
     * @return
     */
    List<IntegratedReservationAvailableDto> getAvailableNums(String data);

    /**
     * 预约申请
     * @param data
     * @return
     */
    String apply(String data);

    /**
     * 取消预约
     * @param data
     */
    void cancel(String data);

    /**
     * 根据第三方订单ID获取预约状态
     * @param data
     * @return
     */
    IntegratedReservationStatusDto getStatus(String data);

    /**
     * 查询已预约的信息，如果未预约返回Null
     * @param systemId
     * @param bizOrderNum
     * @return
     */
    Reservation getReservedReservation(String systemId,String bizOrderNum);

    /**
     * 使用第三方套餐id获取套餐
     * @param bizComboId
     * @return
     */
    Createmeal getMealByBizComboId(String bizComboId,String systemId);
}
