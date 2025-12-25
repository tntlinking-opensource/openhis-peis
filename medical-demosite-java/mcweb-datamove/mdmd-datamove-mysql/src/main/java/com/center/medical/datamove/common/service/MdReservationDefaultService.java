package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdReservationDefault;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 预约各检区默认设置(MdReservationDefault)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:18
 */
public interface MdReservationDefaultService extends IService<MdReservationDefault> {

    /**
     * 分页查询[预约各检区默认设置]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdReservationDefault> getPage(PageParam<MdReservationDefault> page, MdReservationDefault param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdReservationDefault getInfoById(Object id);

}

