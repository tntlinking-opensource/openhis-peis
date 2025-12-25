package com.center.medical.reservation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.ReservationReturnVisit;
import org.apache.ibatis.annotations.Param;

/**
 * 预约回访记录(ReservationReturnVisit)表数据库访问层
 *
 * @author ay
 * @since 2023-03-18 08:54:14
 */
public interface ReservationReturnVisitMapper extends BaseMapper<ReservationReturnVisit> {

    /**
     * 分页查询[预约回访记录]列表
     *
     * @param page  分页参数
     * @param param ReservationReturnVisit查询参数
     * @return 分页数据
     */
    IPage<ReservationReturnVisit> getList(PageParam<ReservationReturnVisit> page, @Param("param") ReservationReturnVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReservationReturnVisit getInfoById(@Param("id") String id);

}
