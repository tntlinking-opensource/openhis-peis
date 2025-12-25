package com.center.medical.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.app.bean.model.SuixingPayConfig;
import com.center.medical.app.common.util.PageParam;

/**
 * 随行支付配置参数(SuixingPayConfig)服务接口
 *
 * @author ay
 * @since 2024-07-12 17:05:40
 */
public interface SuixingPayConfigService extends IService<SuixingPayConfig> {

    /**
     * 分页查询[随行支付配置参数]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SuixingPayConfig> getPage(PageParam<SuixingPayConfig> page, SuixingPayConfig param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SuixingPayConfig getInfoById(String id);

    /**
     * 通过分中心id查询
     * @param branchId
     * @return
     */
    SuixingPayConfig getInfoByFzx(String branchId);
}

