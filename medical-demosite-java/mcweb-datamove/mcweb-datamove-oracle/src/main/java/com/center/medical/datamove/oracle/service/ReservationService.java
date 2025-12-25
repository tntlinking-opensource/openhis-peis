package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Reservation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 预约管理(Reservation)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:19
 */
public interface ReservationService extends IService<Reservation> {

    /**
     * 分页查询[预约管理]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Reservation> getPage(PageParam<Reservation> page, Reservation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Reservation getInfoById(String id);

}

