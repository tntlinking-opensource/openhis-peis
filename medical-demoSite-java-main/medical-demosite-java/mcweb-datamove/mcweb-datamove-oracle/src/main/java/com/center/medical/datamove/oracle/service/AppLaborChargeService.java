package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.AppLaborCharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (AppLaborCharge)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:11:43
 */
public interface AppLaborChargeService extends IService<AppLaborCharge> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppLaborCharge> getPage(PageParam<AppLaborCharge> page, AppLaborCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppLaborCharge getInfoById(String id);

}

