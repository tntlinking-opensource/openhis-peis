package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.SampleDelivery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS样本录入(SampleDelivery)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:33
 */
public interface SampleDeliveryService extends IService<SampleDelivery> {

    /**
     * 分页查询[KS样本录入]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SampleDelivery> getPage(PageParam<SampleDelivery> page, SampleDelivery param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SampleDelivery getInfoById(String id);

}

