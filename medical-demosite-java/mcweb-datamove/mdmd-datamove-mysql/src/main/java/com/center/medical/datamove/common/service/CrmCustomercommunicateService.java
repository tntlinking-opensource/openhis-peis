package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.CrmCustomercommunicate;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 客户沟通表(CrmCustomercommunicate)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:44:59
 */
public interface CrmCustomercommunicateService extends IService<CrmCustomercommunicate> {

    /**
     * 分页查询[客户沟通表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CrmCustomercommunicate> getPage(PageParam<CrmCustomercommunicate> page, CrmCustomercommunicate param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmCustomercommunicate getInfoById(String id);

}

