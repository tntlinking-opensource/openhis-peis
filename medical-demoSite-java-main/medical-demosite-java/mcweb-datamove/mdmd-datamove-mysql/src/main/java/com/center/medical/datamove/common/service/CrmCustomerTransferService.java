package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.CrmCustomerTransfer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。(CrmCustomerTransfer)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:44:58
 */
public interface CrmCustomerTransferService extends IService<CrmCustomerTransfer> {

    /**
     * 分页查询[客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CrmCustomerTransfer> getPage(PageParam<CrmCustomerTransfer> page, CrmCustomerTransfer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmCustomerTransfer getInfoById(String id);

}

