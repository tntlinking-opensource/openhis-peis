package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdReservation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检预约记录(MdReservation)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:17
 */
public interface MdReservationService extends IService<MdReservation> {

    /**
     * 分页查询[体检预约记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdReservation> getPage(PageParam<MdReservation> page, MdReservation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdReservation getInfoById(Long id);

}

