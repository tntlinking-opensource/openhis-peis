package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.PeisReserPayway;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者结算方式表(PeisReserPayway)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:02
 */
public interface PeisReserPaywayService extends IService<PeisReserPayway> {

    /**
     * 分页查询[体检者结算方式表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PeisReserPayway> getPage(PageParam<PeisReserPayway> page, PeisReserPayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeisReserPayway getInfoById(String id);

}

