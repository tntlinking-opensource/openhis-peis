package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.KdOrganization;

/**
 * kingdeeorganization(KdOrganization)表服务接口
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:43
 */
public interface KdOrganizationService extends IService<KdOrganization> {

    /**
     * 分页查询[kingdeeorganization]列表
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

    /**
     *金蝶组织更新
     */
    void upgradeKingdeeOrganization();

}

