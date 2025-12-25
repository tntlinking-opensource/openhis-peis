package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppReservationRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 小程序预约记录（团检+个检）(AppReservationRecord)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:00
 */
public interface AppReservationRecordService extends IService<AppReservationRecord> {

    /**
     * 分页查询[小程序预约记录（团检+个检）]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppReservationRecord> getPage(PageParam<AppReservationRecord> page, AppReservationRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppReservationRecord getInfoById(String id);

}

