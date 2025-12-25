package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.KdOrganizationMapper;
import com.center.medical.datamove.common.bean.model.KdOrganization;
import com.center.medical.datamove.common.service.KdOrganizationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 金蝶中的组织信息（kingdeeorganization）(KdOrganization)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:04
 */
@Slf4j
@Service("kdOrganizationService")
@RequiredArgsConstructor
public class KdOrganizationServiceImpl extends ServiceImpl<KdOrganizationMapper, KdOrganization> implements KdOrganizationService {

    private final KdOrganizationMapper kdOrganizationMapper;

    /**
     * 分页查询[金蝶中的组织信息（kingdeeorganization）]列表
     *
     * @param page  分页参数
     * @param param KdOrganization查询参数
     * @return 分页数据
     */
    @Override
    public IPage<KdOrganization> getPage(PageParam<KdOrganization> page, KdOrganization param) {
        return kdOrganizationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键orgId
     * @return 详情信息
     */
    @Override
    public KdOrganization getInfoById(String id) {
        return kdOrganizationMapper.getInfoById(id);
    }

}


