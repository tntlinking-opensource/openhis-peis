package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Sellcustomer;
import com.center.medical.datamove.admin.dao.OrSellcustomerMapper;
import com.center.medical.datamove.admin.service.OrSellcustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 我的客户表(Sellcustomer)服务实现类
 *
 * @author ay
 * @since 2023-07-25 21:01:02
 */
@Slf4j
@Service("orSellcustomerService")
@RequiredArgsConstructor
public class OrSellcustomerServiceImpl extends ServiceImpl<OrSellcustomerMapper, Sellcustomer> implements OrSellcustomerService {

    private final OrSellcustomerMapper orSellcustomerMapper;

    /**
     * 分页查询[我的客户表]列表
     *
     * @param page  分页参数
     * @param param Sellcustomer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Sellcustomer> getPage(PageParam<Sellcustomer> page, Sellcustomer param) {
        return orSellcustomerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public Sellcustomer getInfoById(String id) {
        return orSellcustomerMapper.getInfoById(id);
    }

}


