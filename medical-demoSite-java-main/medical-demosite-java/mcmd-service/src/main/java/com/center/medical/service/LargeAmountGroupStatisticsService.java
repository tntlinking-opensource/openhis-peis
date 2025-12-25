package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.LargeAmountGroupStatistics;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 年超20万额度单位统计(LargeAmountGroupStatistics)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:19
 */
public interface LargeAmountGroupStatisticsService extends IService<LargeAmountGroupStatistics> {

    /**
     * 分页查询[年超20万额度单位统计]列表
     *
     * @param page  分页参数
     * @param param LargeAmountGroupStatistics查询参数
     * @return 分页数据
     */
    IPage<LargeAmountGroupStatistics> getList(PageParam<LargeAmountGroupStatistics> page, LargeAmountGroupStatistics param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    LargeAmountGroupStatistics getInfoById(String id);

}

