package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PeisReserPayway;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 体检者结算方式表(PeisReserPayway)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:17
 */
public interface PeisReserPaywayService extends IService<PeisReserPayway> {

    /**
     * 分页查询[体检者结算方式表]列表
     *
     * @param page  分页参数
     * @param param PeisReserPayway查询参数
     * @return 分页数据
     */
    IPage<PeisReserPayway> getList(PageParam<PeisReserPayway> page, PeisReserPayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeisReserPayway getInfoById(String id);

    /**
     * 获取结算信息
     *
     * @param id
     * @return
     */
    List<PeisReserPayway> getBillingData(String id);
}

