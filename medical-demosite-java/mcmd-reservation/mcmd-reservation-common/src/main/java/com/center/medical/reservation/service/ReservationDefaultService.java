package com.center.medical.reservation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.ReservationDefault;
import com.center.medical.reservation.bean.param.ReservationPageParam;

/**
 * 预约各检区默认设置(ReservationDefault)表服务接口
 *
 * @author ay
 * @since 2023-03-18 08:54:14
 */
public interface ReservationDefaultService extends IService<ReservationDefault> {

    /**
     * 分页查询[预约各检区默认设置]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReservationDefault> getList(PageParam<ReservationDefault> page, ReservationPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReservationDefault getInfoById(String id);

}

