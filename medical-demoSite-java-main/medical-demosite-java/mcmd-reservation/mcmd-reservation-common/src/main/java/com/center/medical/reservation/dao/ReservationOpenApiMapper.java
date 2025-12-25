package com.center.medical.reservation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.dto.GroupOrderDto;
import com.center.medical.bean.dto.ItemDto;
import com.center.medical.reservation.bean.model.Reservation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检预约外部数据库访问层
 *
 * @author ay
 * @since 2023-03-18 08:54:13
 */
public interface ReservationOpenApiMapper extends BaseMapper<Reservation> {


    /**
     * 根据手机号获取我的待预约团体订单列表
     *
     * @param phone
     * @return
     */
    List<GroupOrderDto> getGroupOrderList(@Param("phone") String phone);

    /**
     * 根据体检号获取体检者收费项目列表
     *
     * @param patientcode 体检号
     * @return
     */
    List<ItemDto> getExamItemList(@Param("patientcode") String patientcode);

    /**
     * 获取我的待预约个检订单
     * @param phone
     * @return
     */
    List<GroupOrderDto> getPersonList(@Param("phone") String phone);
}
