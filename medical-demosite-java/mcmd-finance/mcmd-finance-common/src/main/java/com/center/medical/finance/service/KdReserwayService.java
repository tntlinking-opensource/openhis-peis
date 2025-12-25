package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.KdReserway;

/**
 * kingdeereserway(KdReserway)表服务接口
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:44
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

