package com.center.medical.reservation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.ReservationGroupTime;
import org.apache.ibatis.annotations.Param;

/**
 * 团体预约和预约设置关联表(ReservationGroupTime)数据库访问层
 *
 * @author ay
 * @since 2024-04-26 13:58:00
 */
public interface ReservationGroupTimeMapper extends BaseMapper<ReservationGroupTime> {

    /**
     * 分页查询[团体预约和预约设置关联表]列表
     *
     * @param page  分页参数
     * @param param ReservationGroupTime查询参数
     * @return 分页数据
     */
    IPage<ReservationGroupTime> getPage(PageParam<ReservationGroupTime> page, @Param("param") ReservationGroupTime param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReservationGroupTime getInfoById(@Param("id") String id);

}
