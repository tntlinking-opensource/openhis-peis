package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.ProfessionCustomerMapper;
import com.center.medical.datamove.oracle.bean.model.ProfessionCustomer;
import com.center.medical.datamove.oracle.service.ProfessionCustomerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (ProfessionCustomer)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:40
 */
@Slf4j
@Service("professionCustomerService")
@RequiredArgsConstructor
public class ProfessionCustomerServiceImpl extends ServiceImpl<ProfessionCustomerMapper, ProfessionCustomer> implements ProfessionCustomerService {

    private final ProfessionCustomerMapper professionCustomerMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param ProfessionCustomer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ProfessionCustomer> getPage(PageParam<ProfessionCustomer> page, ProfessionCustomer param) {
        return professionCustomerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ProfessionCustomer getInfoById(String id) {
        return professionCustomerMapper.getInfoById(id);
    }

}


