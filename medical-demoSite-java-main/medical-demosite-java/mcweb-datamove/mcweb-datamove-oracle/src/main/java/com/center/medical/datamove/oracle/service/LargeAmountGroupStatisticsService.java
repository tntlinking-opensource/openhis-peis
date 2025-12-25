package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.LargeAmountGroupStatistics;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 年超20万额度单位统计(LargeAmountGroupStatistics)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:19:34
 */
public interface LargeAmountGroupStatisticsService extends IService<LargeAmountGroupStatistics> {

    /**
     * 分页查询[年超20万额度单位统计]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<LargeAmountGroupStatistics> getPage(PageParam<LargeAmountGroupStatistics> page, LargeAmountGroupStatistics param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    LargeAmountGroupStatistics getInfoById(String id);

}

