package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.FxDetectionzy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * TJ综合分析-检出人数（职业）(FxDetectionzy)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:50
 */
public interface FxDetectionzyService extends IService<FxDetectionzy> {

    /**
     * 分页查询[TJ综合分析-检出人数（职业）]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<FxDetectionzy> getPage(PageParam<FxDetectionzy> page, FxDetectionzy param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    FxDetectionzy getInfoById(String id);

}

