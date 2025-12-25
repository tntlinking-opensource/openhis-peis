package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdReservationReturnVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 预约回访记录(MdReservationReturnVisit)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:19
 */
public interface MdReservationReturnVisitService extends IService<MdReservationReturnVisit> {

    /**
     * 分页查询[预约回访记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdReservationReturnVisit> getPage(PageParam<MdReservationReturnVisit> page, MdReservationReturnVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdReservationReturnVisit getInfoById(String id);

}

