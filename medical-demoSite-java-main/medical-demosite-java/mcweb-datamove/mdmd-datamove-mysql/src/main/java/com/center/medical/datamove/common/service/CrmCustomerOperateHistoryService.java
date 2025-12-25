package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.CrmCustomerOperateHistory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放(CrmCustomerOperateHistory)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:44:58
 */
public interface CrmCustomerOperateHistoryService extends IService<CrmCustomerOperateHistory> {

    /**
     * 分页查询[客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CrmCustomerOperateHistory> getPage(PageParam<CrmCustomerOperateHistory> page, CrmCustomerOperateHistory param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmCustomerOperateHistory getInfoById(String id);

}

