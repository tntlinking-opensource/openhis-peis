package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrSellcustomer;

/**
 * 我的客户表(Sellcustomer)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:01:01
 */
public interface OrSellcustomerService extends IService<OrSellcustomer> {

    /**
     * 分页查询[我的客户表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrSellcustomer> getPage(PageParam<OrSellcustomer> page, OrSellcustomer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrSellcustomer getInfoById(String id);

}

