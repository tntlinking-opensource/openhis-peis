package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.KdRemittance;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * kingdeeremittance(KdRemittance)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:04
 */
public interface KdRemittanceService extends IService<KdRemittance> {

    /**
     * 分页查询[kingdeeremittance]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<KdRemittance> getPage(PageParam<KdRemittance> page, KdRemittance param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键fid
     * @return 详情信息
     */
    KdRemittance getInfoById(String id);

}

