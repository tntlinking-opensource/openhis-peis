package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.CrmCustomerfollowMapper;
import com.center.medical.datamove.common.bean.model.CrmCustomerfollow;
import com.center.medical.datamove.common.service.CrmCustomerfollowService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户跟踪表(CrmCustomerfollow)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:44:59
 */
@Slf4j
@Service("crmCustomerfollowService")
@RequiredArgsConstructor
public class CrmCustomerfollowServiceImpl extends ServiceImpl<CrmCustomerfollowMapper, CrmCustomerfollow> implements CrmCustomerfollowService {

    private final CrmCustomerfollowMapper crmCustomerfollowMapper;

    /**
     * 分页查询[客户跟踪表]列表
     *
     * @param page  分页参数
     * @param param CrmCustomerfollow查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CrmCustomerfollow> getPage(PageParam<CrmCustomerfollow> page, CrmCustomerfollow param) {
        return crmCustomerfollowMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CrmCustomerfollow getInfoById(String id) {
        return crmCustomerfollowMapper.getInfoById(id);
    }

}


