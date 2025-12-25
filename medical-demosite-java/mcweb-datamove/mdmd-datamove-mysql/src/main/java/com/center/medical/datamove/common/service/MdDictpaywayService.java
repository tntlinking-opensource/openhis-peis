package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdDictpayway;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC支付方式表(MdDictpayway)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:13
 */
public interface MdDictpaywayService extends IService<MdDictpayway> {

    /**
     * 分页查询[JC支付方式表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdDictpayway> getPage(PageParam<MdDictpayway> page, MdDictpayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDictpayway getInfoById(String id);

}

