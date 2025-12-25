package com.center.medical.reservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.ReservationGroupTime;

/**
 * 团体预约和预约设置关联表(ReservationGroupTime)服务接口
 *
 * @author ay
 * @since 2024-04-26 13:58:00
 */
public interface ReservationGroupTimeService extends IService<ReservationGroupTime> {

    /**
     * 分页查询[团体预约和预约设置关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReservationGroupTime> getPage(PageParam<ReservationGroupTime> page, ReservationGroupTime param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReservationGroupTime getInfoById(String id);

}

