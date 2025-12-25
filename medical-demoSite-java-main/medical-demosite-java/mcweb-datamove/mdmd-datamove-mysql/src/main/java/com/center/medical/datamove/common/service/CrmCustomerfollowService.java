package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.CrmCustomerfollow;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 客户跟踪表(CrmCustomerfollow)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:44:59
 */
public interface CrmCustomerfollowService extends IService<CrmCustomerfollow> {

    /**
     * 分页查询[客户跟踪表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CrmCustomerfollow> getPage(PageParam<CrmCustomerfollow> page, CrmCustomerfollow param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmCustomerfollow getInfoById(String id);

}

