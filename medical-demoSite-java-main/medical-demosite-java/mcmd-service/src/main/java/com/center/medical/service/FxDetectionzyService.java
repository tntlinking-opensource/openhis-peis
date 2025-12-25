package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.FxDetectionzy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * TJ综合分析-检出人数（职业）(FxDetectionzy)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:37
 */
public interface FxDetectionzyService extends IService<FxDetectionzy> {

    /**
     * 分页查询[TJ综合分析-检出人数（职业）]列表
     *
     * @param page  分页参数
     * @param param FxDetectionzy查询参数
     * @return 分页数据
     */
    IPage<FxDetectionzy> getList(PageParam<FxDetectionzy> page, FxDetectionzy param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FxDetectionzy getInfoById(String id);

}

