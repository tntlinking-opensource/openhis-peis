package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppReservationGroupTime;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 小程序团检预约各时间段人数(AppReservationGroupTime)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:11:57
 */
public interface AppReservationGroupTimeService extends IService<AppReservationGroupTime> {

    /**
     * 分页查询[小程序团检预约各时间段人数]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppReservationGroupTime> getPage(PageParam<AppReservationGroupTime> page, AppReservationGroupTime param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppReservationGroupTime getInfoById(String id);

}

