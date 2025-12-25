package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.CustomerOperateHistory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 记录客户释放、领取、流失、分配、领导释放、线程释放(CustomerOperateHistory)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:02
 */
public interface CustomerOperateHistoryService extends IService<CustomerOperateHistory> {

    /**
     * 分页查询[记录客户释放、领取、流失、分配、领导释放、线程释放]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CustomerOperateHistory> getPage(PageParam<CustomerOperateHistory> page, CustomerOperateHistory param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CustomerOperateHistory getInfoById(String id);

}

