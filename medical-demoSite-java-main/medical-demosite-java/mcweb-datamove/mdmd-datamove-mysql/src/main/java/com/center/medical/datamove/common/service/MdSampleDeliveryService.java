package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdSampleDelivery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS样本录入(MdSampleDelivery)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:25
 */
public interface MdSampleDeliveryService extends IService<MdSampleDelivery> {

    /**
     * 分页查询[KS样本录入]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSampleDelivery> getPage(PageParam<MdSampleDelivery> page, MdSampleDelivery param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSampleDelivery getInfoById(String id);

}

