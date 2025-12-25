package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Comboandfzx;

/**
 * 最小套餐与分中心关联表(Comboandfzx)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:48
 */
public interface ComboandfzxService extends IService<Comboandfzx> {

    /**
     * 分页查询[最小套餐与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param Comboandfzx查询参数
     * @return 分页数据
     */
    IPage<Comboandfzx> getList(PageParam<Comboandfzx> page, Comboandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Comboandfzx getInfoById(String id);

}

