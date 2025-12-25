package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.KdReser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 每笔银行汇款结算详情(KdReser)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:04
 */
public interface KdReserService extends IService<KdReser> {

    /**
     * 分页查询[每笔银行汇款结算详情]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<KdReser> getPage(PageParam<KdReser> page, KdReser param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    KdReser getInfoById(String id);

}

