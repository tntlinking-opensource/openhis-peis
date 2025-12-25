package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.CrmSellpactMapper;
import com.center.medical.datamove.common.bean.model.CrmSellpact;
import com.center.medical.datamove.common.service.CrmSellpactService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 销售合同维护表(CrmSellpact)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:01
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

}


