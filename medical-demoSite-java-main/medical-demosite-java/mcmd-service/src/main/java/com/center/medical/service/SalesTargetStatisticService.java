package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.SalesTargetStatistic;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 销售目标自动统计(SalesTargetStatistic)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:03
 */
public interface SalesTargetStatisticService extends IService<SalesTargetStatistic> {

    /**
     * 分页查询[销售目标自动统计]列表
     *
     * @param page  分页参数
     * @param param SalesTargetStatistic查询参数
     * @return 分页数据
     */
    IPage<SalesTargetStatistic> getList(PageParam<SalesTargetStatistic> page, SalesTargetStatistic param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    SalesTargetStatistic getInfoById(String id);

    /**
     * 销售目标自动统计年度计划
     * @param year
     * @return
     */
    List<SalesTargetStatistic> selectLeaderList(String year);

    /**
     * 销售目标自动统计季度计划
     * @param year
     * @return
     */
    List<SalesTargetStatistic> selectSellList(String year);

    /**
     * 销售目标自动统计月度计划
     * @param year
     * @return
     */
    List<SalesTargetStatistic> selectMonthList(String year);
}

