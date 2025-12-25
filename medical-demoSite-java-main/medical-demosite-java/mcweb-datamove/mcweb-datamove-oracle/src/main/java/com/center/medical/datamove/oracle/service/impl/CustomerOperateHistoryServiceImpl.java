package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.CustomerOperateHistoryMapper;
import com.center.medical.datamove.oracle.bean.model.CustomerOperateHistory;
import com.center.medical.datamove.oracle.service.CustomerOperateHistoryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 记录客户释放、领取、流失、分配、领导释放、线程释放(CustomerOperateHistory)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:02
 */
@Slf4j
@Service("customerOperateHistoryService")
@RequiredArgsConstructor
public class CustomerOperateHistoryServiceImpl extends ServiceImpl<CustomerOperateHistoryMapper, CustomerOperateHistory> implements CustomerOperateHistoryService {

    private final CustomerOperateHistoryMapper customerOperateHistoryMapper;

    /**
     * 分页查询[记录客户释放、领取、流失、分配、领导释放、线程释放]列表
     *
     * @param page  分页参数
     * @param param CustomerOperateHistory查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CustomerOperateHistory> getPage(PageParam<CustomerOperateHistory> page, CustomerOperateHistory param) {
        return customerOperateHistoryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CustomerOperateHistory getInfoById(String id) {
        return customerOperateHistoryMapper.getInfoById(id);
    }

}


