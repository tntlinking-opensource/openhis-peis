package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.CrmSellcustomer;

/**
 * 我的客户表(CrmSellcustomer)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:02:12
 */
public interface CrmSellcustomerService extends IService<CrmSellcustomer> {

    /**
     * 分页查询[我的客户表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CrmSellcustomer> getPage(PageParam<CrmSellcustomer> page, CrmSellcustomer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmSellcustomer getInfoById(String id);

    /**
     * 添加或更新
     *
     * @param map
     */
    void saOrUp(CrmSellcustomer map);
}

