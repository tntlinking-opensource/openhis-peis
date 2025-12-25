package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.SalesTargetStatistic;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 销售目标自动统计(SalesTargetStatistic)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:31
 */
public interface SalesTargetStatisticService extends IService<SalesTargetStatistic> {

    /**
     * 分页查询[销售目标自动统计]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SalesTargetStatistic> getPage(PageParam<SalesTargetStatistic> page, SalesTargetStatistic param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SalesTargetStatistic getInfoById(String id);

}

