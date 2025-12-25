package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.CrmSellpact;

/**
 * 销售合同维护表(CrmSellpact)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:19:53
 */
public interface CrmSellpactService extends IService<CrmSellpact> {

    /**
     * 分页查询[销售合同维护表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CrmSellpact> getPage(PageParam<CrmSellpact> page, CrmSellpact param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmSellpact getInfoById(String id);

    /**
     * 保存或更新
     *
     * @param map
     */
    void saOrUp(CrmSellpact map);
}

