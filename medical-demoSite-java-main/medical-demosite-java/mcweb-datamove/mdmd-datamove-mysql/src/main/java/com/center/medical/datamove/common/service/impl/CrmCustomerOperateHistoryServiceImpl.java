package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.CrmCustomerOperateHistoryMapper;
import com.center.medical.datamove.common.bean.model.CrmCustomerOperateHistory;
import com.center.medical.datamove.common.service.CrmCustomerOperateHistoryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放(CrmCustomerOperateHistory)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:44:58
 */
@Slf4j
@Service("crmCustomerOperateHistoryService")
@RequiredArgsConstructor
public class CrmCustomerOperateHistoryServiceImpl extends ServiceImpl<CrmCustomerOperateHistoryMapper, CrmCustomerOperateHistory> implements CrmCustomerOperateHistoryService {

    private final CrmCustomerOperateHistoryMapper crmCustomerOperateHistoryMapper;

    /**
     * 分页查询[客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放]列表
     *
     * @param page  分页参数
     * @param param CrmCustomerOperateHistory查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CrmCustomerOperateHistory> getPage(PageParam<CrmCustomerOperateHistory> page, CrmCustomerOperateHistory param) {
        return crmCustomerOperateHistoryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CrmCustomerOperateHistory getInfoById(String id) {
        return crmCustomerOperateHistoryMapper.getInfoById(id);
    }

}


