package com.center.medical.datamove.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.CrmSellpact;
import com.center.medical.datamove.admin.dao.CrmSellpactMapper;
import com.center.medical.datamove.admin.service.CrmSellpactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 销售合同维护表(CrmSellpact)服务实现类
 *
 * @author ay
 * @since 2023-07-25 21:19:54
 */
@Slf4j
@Service("crmSellpactService")
@RequiredArgsConstructor
public class CrmSellpactServiceImpl extends ServiceImpl<CrmSellpactMapper, CrmSellpact> implements CrmSellpactService {

    private final CrmSellpactMapper crmSellpactMapper;

    /**
     * 分页查询[销售合同维护表]列表
     *
     * @param page  分页参数
     * @param param CrmSellpact查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CrmSellpact> getPage(PageParam<CrmSellpact> page, CrmSellpact param) {
        return crmSellpactMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CrmSellpact getInfoById(String id) {
        return crmSellpactMapper.getInfoById(id);
    }

    /**
     * 保存或更新
     *
     * @param map
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public void saOrUp(CrmSellpact map) {
        saveOrUpdate(map);
    }
}


