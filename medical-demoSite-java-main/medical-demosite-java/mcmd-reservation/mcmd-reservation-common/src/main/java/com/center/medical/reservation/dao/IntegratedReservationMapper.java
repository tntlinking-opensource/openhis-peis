package com.center.medical.reservation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.sellcrm.bean.model.Createorder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 新老系统集成预约
 * @author xhp
 * @since 2024-01-03 8:04
 */
@Repository
public interface IntegratedReservationMapper extends BaseMapper<Reservation> {
    /**
     * 根据套餐id查询订单列表
     * @param mealId
     * @return
     */
    List<Createorder> getOrderListByMealId(@Param("mealId") String mealId);
}
