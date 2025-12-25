package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.CrmSellOutside;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 外出沟通(CrmSellOutside)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:00
 */
public interface CrmSellOutsideService extends IService<CrmSellOutside> {

    /**
     * 分页查询[外出沟通]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CrmSellOutside> getPage(PageParam<CrmSellOutside> page, CrmSellOutside param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmSellOutside getInfoById(String id);

}

