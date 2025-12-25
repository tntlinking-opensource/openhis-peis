package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.CrmCustomerTransferMapper;
import com.center.medical.datamove.common.bean.model.CrmCustomerTransfer;
import com.center.medical.datamove.common.service.CrmCustomerTransferService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。(CrmCustomerTransfer)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:44:58
 */
@Slf4j
@Service("crmCustomerTransferService")
@RequiredArgsConstructor
public class CrmCustomerTransferServiceImpl extends ServiceImpl<CrmCustomerTransferMapper, CrmCustomerTransfer> implements CrmCustomerTransferService {

    private final CrmCustomerTransferMapper crmCustomerTransferMapper;

    /**
     * 分页查询[客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。]列表
     *
     * @param page  分页参数
     * @param param CrmCustomerTransfer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CrmCustomerTransfer> getPage(PageParam<CrmCustomerTransfer> page, CrmCustomerTransfer param) {
        return crmCustomerTransferMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CrmCustomerTransfer getInfoById(String id) {
        return crmCustomerTransferMapper.getInfoById(id);
    }

}


