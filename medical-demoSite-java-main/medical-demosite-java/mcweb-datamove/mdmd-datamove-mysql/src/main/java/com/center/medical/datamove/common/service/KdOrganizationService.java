package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.KdOrganization;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 金蝶中的组织信息（kingdeeorganization）(KdOrganization)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:04
 */
public interface KdOrganizationService extends IService<KdOrganization> {

    /**
     * 分页查询[金蝶中的组织信息（kingdeeorganization）]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<KdOrganization> getPage(PageParam<KdOrganization> page, KdOrganization param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键orgId
     * @return 详情信息
     */
    KdOrganization getInfoById(String id);

}

