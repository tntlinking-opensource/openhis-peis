package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.CrmSellcustomerMapper;
import com.center.medical.datamove.common.bean.model.CrmSellcustomer;
import com.center.medical.datamove.common.service.CrmSellcustomerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 我的客户表(CrmSellcustomer)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:01
 */
@Slf4j
@Service("crmSellcustomerService")
@RequiredArgsConstructor
public class CrmSellcustomerServiceImpl extends ServiceImpl<CrmSellcustomerMapper, CrmSellcustomer> implements CrmSellcustomerService {

    private final CrmSellcustomerMapper crmSellcustomerMapper;

    /**
     * 分页查询[我的客户表]列表
     *
     * @param page  分页参数
     * @param param CrmSellcustomer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CrmSellcustomer> getPage(PageParam<CrmSellcustomer> page, CrmSellcustomer param) {
        return crmSellcustomerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CrmSellcustomer getInfoById(String id) {
        return crmSellcustomerMapper.getInfoById(id);
    }

}


