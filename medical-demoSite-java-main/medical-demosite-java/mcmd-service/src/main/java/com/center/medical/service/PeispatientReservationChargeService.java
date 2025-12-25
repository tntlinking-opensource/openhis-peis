package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PeispatientReservationCharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者结算表(PeispatientReservationCharge)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:03
 */
public interface PeispatientReservationChargeService extends IService<PeispatientReservationCharge> {

    /**
     * 分页查询[体检者结算表]列表
     *
     * @param page  分页参数
     * @param param PeispatientReservationCharge查询参数
     * @return 分页数据
     */
    IPage<PeispatientReservationCharge> getList(PageParam<PeispatientReservationCharge> page, PeispatientReservationCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeispatientReservationCharge getInfoById(String id);

}

