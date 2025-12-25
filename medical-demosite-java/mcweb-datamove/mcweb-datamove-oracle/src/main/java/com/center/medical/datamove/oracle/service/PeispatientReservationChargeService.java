package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.PeispatientReservationCharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者结算表(PeispatientReservationCharge)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:24
 */
public interface PeispatientReservationChargeService extends IService<PeispatientReservationCharge> {

    /**
     * 分页查询[体检者结算表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PeispatientReservationCharge> getPage(PageParam<PeispatientReservationCharge> page, PeispatientReservationCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeispatientReservationCharge getInfoById(String id);

}

