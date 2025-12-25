package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.KdReserway;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * kingdeereserway(KdReserway)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:05
 */
public interface KdReserwayService extends IService<KdReserway> {

    /**
     * 分页查询[kingdeereserway]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<KdReserway> getPage(PageParam<KdReserway> page, KdReserway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    KdReserway getInfoById(String id);

}

