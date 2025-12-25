package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.CrmSelltarget;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * XS销售目标(CrmSelltarget)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:02
 */
public interface CrmSelltargetService extends IService<CrmSelltarget> {

    /**
     * 分页查询[XS销售目标]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CrmSelltarget> getPage(PageParam<CrmSelltarget> page, CrmSelltarget param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmSelltarget getInfoById(String id);

}

