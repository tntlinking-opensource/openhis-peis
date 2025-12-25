package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.KdCustomerMapper;
import com.center.medical.datamove.common.bean.model.KdCustomer;
import com.center.medical.datamove.common.service.KdCustomerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 金碟账户(KdCustomer)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:03
 */
@Slf4j
@Service("kdCustomerService")
@RequiredArgsConstructor
public class KdCustomerServiceImpl extends ServiceImpl<KdCustomerMapper, KdCustomer> implements KdCustomerService {

    private final KdCustomerMapper kdCustomerMapper;

    /**
     * 分页查询[金碟账户]列表
     *
     * @param page  分页参数
     * @param param KdCustomer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<KdCustomer> getPage(PageParam<KdCustomer> page, KdCustomer param) {
        return kdCustomerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键accountNo
     * @return 详情信息
     */
    @Override
    public KdCustomer getInfoById(String id) {
        return kdCustomerMapper.getInfoById(id);
    }

}


