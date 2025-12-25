package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.CustomerTransferMapper;
import com.center.medical.datamove.oracle.bean.model.CustomerTransfer;
import com.center.medical.datamove.oracle.service.CustomerTransferService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (CustomerTransfer)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:18:04
 */
@Slf4j
@Service("customerTransferService")
@RequiredArgsConstructor
public class CustomerTransferServiceImpl extends ServiceImpl<CustomerTransferMapper, CustomerTransfer> implements CustomerTransferService {

    private final CustomerTransferMapper customerTransferMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param CustomerTransfer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CustomerTransfer> getPage(PageParam<CustomerTransfer> page, CustomerTransfer param) {
        return customerTransferMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CustomerTransfer getInfoById(String id) {
        return customerTransferMapper.getInfoById(id);
    }

}


