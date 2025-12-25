package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Dictpayway;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC支付方式表(Dictpayway)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:15
 */
public interface DictpaywayService extends IService<Dictpayway> {

    /**
     * 分页查询[JC支付方式表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Dictpayway> getPage(PageParam<Dictpayway> page, Dictpayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Dictpayway getInfoById(String id);

}

