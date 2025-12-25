package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.CrmSellcustomer;
import com.center.medical.datamove.admin.dao.CrmSellcustomerMapper;
import com.center.medical.datamove.admin.service.CrmSellcustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 我的客户表(CrmSellcustomer)服务实现类
 *
 * @author ay
 * @since 2023-07-25 21:02:13
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


    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUp(CrmSellcustomer map) {
        saveOrUpdate(map);
    }
}


