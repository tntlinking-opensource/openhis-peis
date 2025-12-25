package com.center.medical.reservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.ReservationReturnVisit;

/**
 * 预约回访记录(ReservationReturnVisit)表服务接口
 *
 * @author ay
 * @since 2023-03-18 08:54:15
 */
public interface ReservationReturnVisitService extends IService<ReservationReturnVisit> {

    /**
     * 分页查询[预约回访记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReservationReturnVisit> getList(PageParam<ReservationReturnVisit> page, ReservationReturnVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReservationReturnVisit getInfoById(String id);

}

