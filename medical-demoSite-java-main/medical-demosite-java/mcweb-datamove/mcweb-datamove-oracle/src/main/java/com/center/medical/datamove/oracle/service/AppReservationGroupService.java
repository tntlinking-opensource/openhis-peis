package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppReservationGroup;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 小程序团检预约人数(AppReservationGroup)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:11:55
 */
public interface AppReservationGroupService extends IService<AppReservationGroup> {

    /**
     * 分页查询[小程序团检预约人数]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppReservationGroup> getPage(PageParam<AppReservationGroup> page, AppReservationGroup param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppReservationGroup getInfoById(String id);

}

