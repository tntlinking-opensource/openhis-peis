package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Sellcustomer;

/**
 * 我的客户表(Sellcustomer)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:01:01
 */
public interface OrSellcustomerService extends IService<Sellcustomer> {

    /**
     * 分页查询[我的客户表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Sellcustomer> getPage(PageParam<Sellcustomer> page, Sellcustomer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Sellcustomer getInfoById(String id);

}

